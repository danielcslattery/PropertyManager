package PropertyManager.Exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AddressNotFound.class)
    public ModelAndView handleAddressNotFound(HttpServletRequest req, AddressNotFound ex) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("address", ex.getAddress());
        mav.setViewName("Errors/NoBuildingAddress");
        return mav;
    }

    @ExceptionHandler(BuildingIdNotFound.class)
    public ModelAndView handleBuildingIdNotFound(HttpServletRequest req, BuildingIdNotFound ex) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("id", ex.getId());
        mav.setViewName("Errors/NoBuildingId");
        return mav;
    }

    //TODO Add handler for no buildings in database here

    @ExceptionHandler(ApartmentNumberNotFound.class)
    public ModelAndView handleApartmentNumberNotFound(HttpServletRequest req, ApartmentNumberNotFound ex) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("apartmentNumber", ex.getNumber());
        mav.setViewName("Errors/NoApartmentName");
        return mav;
    }


}
