//package com.farmacia_web.farmacia_web.controllers;
//
//
//import com.farmacia_web.farmacia_web.models.EmpleadoDetails;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class GlobalController {
//    @ModelAttribute
//    public void addUserToModel(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            EmpleadoDetails empleadoDetails = (EmpleadoDetails) authentication.getPrincipal();
//            model.addAttribute("usuarioNombre", empleadoDetails.getNombre());
//        }
//    }
//}
