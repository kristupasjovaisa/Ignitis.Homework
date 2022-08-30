package ignitis_homework.controllers;

import ignitis_homework.common.OpenApi;
import ignitis_homework.dto.UserReportResponse;
import ignitis_homework.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@OpenApi
@Api(tags = "Reports Controller")
@ApiResponses(value = @ApiResponse(code = 401, message = "User must be authorized"))
@RequestMapping(ReportsApiController.REPORTS_ROOT_PATH)
@RequiredArgsConstructor
public class ReportsApiController {
    private final UserService userService;
    public static final String REPORTS_ROOT_PATH = "/reports";
    public static final String REPORTS_USERS_PATH = "/users";
    public static final String REPORTS_USERS_USERID_PATH = REPORTS_USERS_PATH + "/{userId}";

    @GetMapping(path = REPORTS_USERS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all the reports about the users", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reports returned successfully"),
            @ApiResponse(code = 403, message = "User is not granted to get the reports")
    })
    public ResponseEntity<List<UserReportResponse>> getUsersReports() {
        return ResponseEntity.ok(userService.getUserReports());
    }

    @GetMapping(path = REPORTS_USERS_USERID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a report about the user", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Report returned successfully"),
            @ApiResponse(code = 403, message = "User is not granted to get the report")
    })
    public ResponseEntity<UserReportResponse> getUserReportByUserId(@PathVariable("userId") Long userId) {
        var response = userService.getUserReport(userId);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.notFound().build();
    }
}
