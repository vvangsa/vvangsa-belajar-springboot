package id.vvangsa.controller;

import id.vvangsa.model.Customer;
import id.vvangsa.service.CustomerService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class CustomerCtrl {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService){
        this.customerService = customerService;
    }

    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }

    @RequestMapping("/list-customer")
    public String listCustomer(Model model){
        model.addAttribute("Customer", customerService.getAllCustomer());
        return "ListCustomerView";
    }

    @RequestMapping("/list-customer/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id){
        customerService.doDeleteBy(id);
        return "redirect:/list-customer";
    }

    @RequestMapping(value = "/list-customer/edit/{id}", method = RequestMethod.GET)
    public String editCustomer(@PathVariable Integer id, Model model){
        model.addAttribute("Customer", customerService.getCustomerBy(id));
        return "EditCustomerView";
    }

    @RequestMapping(value = "/list-customer/create", method = RequestMethod.GET)
    public String showCustomerForm(Model model){
        model.addAttribute("Customer", new Customer());
        return "EditCustomerView";
    }

    @RequestMapping(value = "/list-customer/create", method = RequestMethod.POST)
    public String createCustomer(Model model, Customer customer){
        model.addAttribute("Customer", customerService.doSaveOrUpdate(customer));
        return "redirect:/list-customer";
    }

    @RequestMapping(value = "/list-customer/download", method = RequestMethod.GET)
    public void printListReport (HttpServletResponse response) throws Exception{
        response.setContentType("text/html");

        List<Customer> customers = customerService.getAllCustomer();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);
        InputStream jrxmlInput = this.getClass().getResourceAsStream("/CetakAllCustomer.jrxml");
        JasperDesign design = JRXmlLoader.load(jrxmlInput);
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        JRPdfExporter pdfExporter = new JRPdfExporter();
        pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
        pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
        pdfExporter.exportReport();

        response.setContentType("application/pdf");
        response.setHeader("Content-Length", String.valueOf(pdfReportStream.size()));
        response.addHeader("Content-Disposition", "inline; cetak-all-customer.pdf;");

        OutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(pdfReportStream.toByteArray());
        responseOutputStream.close();
        pdfReportStream.close();
    }
}
