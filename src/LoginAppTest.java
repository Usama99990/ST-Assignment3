import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class LoginAppTest {

    private LoginApp loginApp;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        // Initialize the LoginApp object and mocks
        loginApp = new LoginApp();

        // Create mock database objects
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    public void testAuthenticateUser_Success() throws Exception {
        // Simulate a successful database query (user found)
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Name")).thenReturn("John Doe");

        // Call the authenticateUser method (it should return 'John Doe')
        String userName = loginApp.authenticateUser("john.doe@example.com");

        // Validate that the returned username is correct
        assertEquals("John Doe", userName);
    }

    @Test
    public void testAuthenticateUser_Failure() throws Exception {
        // Simulate a failed database query (user not found)
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Call the authenticateUser method (it should return null)
        String userName = loginApp.authenticateUser("nonexistent@example.com");

        // Validate that the username is null
        assertNull(userName);
    }

    // More tests can be added here to simulate database errors, etc.
}
