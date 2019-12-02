package it.summer.framework.api.controller;

import it.summer.framework.api.dto.ApiInterface;
import it.summer.framework.api.utils.InterfacesRegister;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author summer's papa on 2019-12-02 09:23:09
 */
@RestController
@RequestMapping("privates/interfaces")
@Order
public class PrivatesInterfacesController {
    @GetMapping
    List<ApiInterface> interfaces() throws Exception {
        return InterfacesRegister.register();
    }
}