package ignitis_homework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ignitis_homework.dto.UserReportResponse;
import ignitis_homework.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportApiController.class)
class ReportApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    ReportApiController reportsApiController;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getUsersReports() throws Exception {
        var response = List.of(UserReportResponse.builder().lastMessageText("text").build());
        when(userService.getUserReports()).thenReturn(response);
        mockMvc.perform((MockMvcRequestBuilders.get("/reports/users")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void getUserReportByUserId() throws Exception {
        var response = UserReportResponse.builder().lastMessageText("text").build();
        when(userService.getUserReport(1l)).thenReturn(Optional.of(response));
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/users/{userId}",1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}