package PropertyManager.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ApartmentServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApartmentService apartmentService;
 
    // @Test
	// void loadsAllApartments() {
    //     // when(apartmentService
    //     //         .getAll()
    //     //         .thenReturn())

    //     mockMvc.perform(get("/apartments"))
    //         .andDo(print())

	// }

}
