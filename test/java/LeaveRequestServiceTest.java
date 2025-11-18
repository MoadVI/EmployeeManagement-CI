import org.employeemanagement.entities.LeaveRequest;
import org.employeemanagement.repository.interfaces.LeaveRequestRepository;
import org.employeemanagement.service.LeaveRequestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaveRequestServiceTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;

    @Test
    public void testSubmitLeaveRequest() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setApproved(false);

        when(leaveRequestRepository.save(leaveRequest)).thenReturn(leaveRequest);

        LeaveRequest saved = leaveRequestService.submitLeaveRequest(leaveRequest);

        assertNotNull(saved);
        verify(leaveRequestRepository, times(1)).save(leaveRequest);
    }

    @Test
    public void testGetAllLeaveRequests() {
        List<LeaveRequest> list = new ArrayList<>();
        list.add(new LeaveRequest());
        list.add(new LeaveRequest());

        when(leaveRequestRepository.findAll()).thenReturn(list);

        List<LeaveRequest> result = leaveRequestService.getAllLeaveRequests();
        assertEquals(2, result.size());
        verify(leaveRequestRepository, times(1)).findAll();
    }

    @Test
    public void testApproveLeaveRequest_Success() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setApproved(false);
        leaveRequest.setId(1L);

        when(leaveRequestRepository.findById(1L)).thenReturn(leaveRequest);

        leaveRequestService.approveLeaveRequest(1L);

        assertTrue(leaveRequest.isApproved());
        verify(leaveRequestRepository, times(1)).update(leaveRequest);
    }

    @Test
    public void testApproveLeaveRequest_NotFound() {
        when(leaveRequestRepository.findById(999L)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                leaveRequestService.approveLeaveRequest(999L)
        );

        assertEquals("Demande de congé non trouvée", exception.getMessage());
        verify(leaveRequestRepository, times(1)).findById(999L);
    }
}
