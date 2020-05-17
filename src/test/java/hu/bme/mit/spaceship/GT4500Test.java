package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

    private GT4500 ship;
    private TorpedoStore mockPrimaryTorpedoStore;
    private TorpedoStore mockSecondaryTorpedoStore;

    @BeforeEach
    public void init() {
        mockPrimaryTorpedoStore = mock(TorpedoStore.class);
        mockSecondaryTorpedoStore = mock(TorpedoStore.class);
        this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
    }

    @Test
    public void fireTorpedo_Single_Success() {
        // Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
        when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);


        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        verify(mockPrimaryTorpedoStore, times(1)).fire(1);
        verify(mockSecondaryTorpedoStore, times(0)).fire(1);
    }

    @Test
    public void fireTorpedo_All_Success() {
        // Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
        when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);

        when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
        when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);


        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
        verify(mockPrimaryTorpedoStore, times(1)).fire(1);
        verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    }

    @Test
    public void fireTorpedo_Single_Primary_Empty_Success() {
        // Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
        when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
        when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);


        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
        verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    }

    @Test
    public void fireTorpedo_Single_Primary_And_Secondary_Empty_Fail() {
        // Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
        when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void fireTorpedo_All_Both_Empty_Fail(){
        // Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
        when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void fireTorpedo_Single_AfterPrimary_Success() {
        //Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
        when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

        //Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        //Arrange
        when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
        when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

        //Act
        result = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertEquals(true, result);
        verify(mockPrimaryTorpedoStore, times(1)).fire(1);
        verify(mockSecondaryTorpedoStore, times(1)).fire(1);
    }

    @Test
    public void fireTorpedo_Single_AfterPrimary_Secondary_Empty_Success() {
        //Arrange
        when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
        when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

        //Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        //Arrange
        when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

        //Act
        result = ship.fireTorpedo(FiringMode.SINGLE);

        //Assert
        assertEquals(true, result);
        verify(mockPrimaryTorpedoStore, times(2)).fire(1);
    }



}
