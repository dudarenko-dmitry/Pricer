package pl.senla.pricer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.senla.pricer.dao.DaoUserProfile;
import pl.senla.pricer.dto.UserProfileDto;
import pl.senla.pricer.entity.UserProfile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceUserProfileImplTest {

    @InjectMocks
    private ServiceUserProfileImpl serviceUserProfileMock;
    @Mock
    private DaoUserProfile daoUserProfile;

    @Test
    void readAll_success() {
        Map<String, String> requestParams = new HashMap<>();
        UserProfile userProfile1 = new UserProfile(1L, "first_name 1", "last_name 1", 2L);
        UserProfile userProfile2 = new UserProfile(2L, "first_name 2", "last_name 2", 1L);
        UserProfile userProfile3 = new UserProfile(3L, "first_name 3", "last_name 3", 4L);
        List<UserProfile> savedList = new ArrayList<>();
        savedList.add(userProfile1);
        savedList.add(userProfile2);
        savedList.add(userProfile3);

        when(daoUserProfile.findAll()).thenReturn(savedList);

        List<UserProfile> readList = serviceUserProfileMock.readAll(requestParams);

        assertNotNull(readList);
        assertTrue(requestParams.isEmpty());
        assertEquals(savedList, readList);
        verify(daoUserProfile, times(1)).findAll();
    }

    @Test
    void readAll_throwsException() {
        Map<String, String> requestParams = new HashMap<>();
        when(daoUserProfile.findAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> serviceUserProfileMock.readAll(requestParams));
    }

    @Test
    void create_success() {
        UserProfileDto userProfileDto = new UserProfileDto("first_name 1", "last_name 1");
        UserProfile savedUserProfile = new UserProfile(1L, "first_name 1", "last_name 1", 2L);

        when(daoUserProfile.findByFirstNameAndLastName("first_name 1", "last_name 1")).thenReturn(null);
        when(daoUserProfile.save(any(UserProfile.class))).thenReturn(savedUserProfile);

        UserProfile createdUserProfile = serviceUserProfileMock.create(userProfileDto);

        assertNotNull(createdUserProfile);
        assertEquals(savedUserProfile, createdUserProfile);
        verify(daoUserProfile, times(1)).findByFirstNameAndLastName("first_name 1", "last_name 1");
        verify(daoUserProfile, times(1)).save(any(UserProfile.class));
    }

    @Test
    void createNewCategory_forbidden() {
        UserProfile userProfile = new UserProfile(1L, "first_name 1", "last_name 1", 1L);
        UserProfileDto userProfileDto = new UserProfileDto("first_name 1", "last_name 1");

        when(daoUserProfile.findByFirstNameAndLastName("first_name 1", "last_name 1")).thenReturn(userProfile);

        UserProfile createdUserProfile = serviceUserProfileMock.create(userProfileDto);

        assertNull(createdUserProfile);
        verify(daoUserProfile, times(1)).findByFirstNameAndLastName("first_name 1", "last_name 1");
        verify(daoUserProfile, times(0)).save(any(UserProfile.class));
    }

    @Test
    void read_success() {
        UserProfile savedUserProfile = new UserProfile(1L, "first_name 1", "last_name 1", 2L);
        Long idTest = 1L;

        when(daoUserProfile.findById(idTest)).thenReturn(Optional.of(savedUserProfile));

        UserProfile readUserProfile = serviceUserProfileMock.read(idTest);

        assertNotNull(readUserProfile);
        assertEquals(savedUserProfile, readUserProfile);
        verify(daoUserProfile, times(1)).findById(idTest);
    }

    @Test
    void read_throwsException() {
        Long idTest = 2L;
        when(daoUserProfile.findById(idTest)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> serviceUserProfileMock.read(idTest));
        verify(daoUserProfile, times(1)).findById(idTest);
    }

    @Test
    void update_success() {
        UserProfile userProfileOld = new UserProfile(1L, "first_name 1", "last_name 1", 2L);
        UserProfileDto userProfileDtoNew = new UserProfileDto("first_name New", "last_name New");
        UserProfile userProfileNew = new UserProfile(1L, "first_name NEW", "last_name NEW", 2L);
        Long idTest = 1L;

        when(daoUserProfile.findById(idTest)).thenReturn(Optional.of(userProfileOld));
        when(daoUserProfile.save(any(UserProfile.class))).thenReturn(userProfileNew);

        UserProfile userProfileUpdated = serviceUserProfileMock.update(idTest, userProfileDtoNew);

        assertNotNull(userProfileUpdated);
        assertEquals(userProfileNew, userProfileUpdated);
        verify(daoUserProfile, times(1)).findById(idTest);
        verify(daoUserProfile, times(1)).save(any(UserProfile.class));
    }

    @Test
    void update_throwsException() {
        UserProfileDto userProfileDtoNew = new UserProfileDto("first_name 1", "last_name 1");
        Long idTest = 1L;

        when(daoUserProfile.findById(idTest)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> serviceUserProfileMock.update(idTest, userProfileDtoNew));
        verify(daoUserProfile, times(1)).findById(idTest);
    }

    @Test
    void delete_success() {
        Long idTest = 1L;
        when(daoUserProfile.existsById(idTest)).thenReturn(true);
        serviceUserProfileMock.delete(idTest);
        verify(daoUserProfile, times(1)).existsById(idTest);
        verify(daoUserProfile, times(1)).deleteById(idTest);
    }

    @Test
    void delete_throwsException() {
        Long idTest = 1L;
        when(daoUserProfile.existsById(idTest)).thenReturn(false);
        assertThrows(NullPointerException.class, () -> serviceUserProfileMock.delete(idTest));
        verify(daoUserProfile, times(1)).existsById(idTest);
    }
}