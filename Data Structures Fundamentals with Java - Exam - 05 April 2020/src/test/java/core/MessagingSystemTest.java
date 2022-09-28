package core;

import model.Message;
import model.TextMessage;
import org.junit.Before;
import org.junit.Test;
import shared.DataTransferSystem;

import java.util.List;

import static org.junit.Assert.*;

public class MessagingSystemTest {
    private List<Message> messages;

    private DataTransferSystem system;

    @Before
    public void setUp() {
        this.messages = List.of(
                new TextMessage(11, "test_text"),
                new TextMessage(6, "test_text"),
                new TextMessage(19, "test_text"),
                new TextMessage(4, "test_text"),
                new TextMessage(8, "test_text"),
                new TextMessage(17, "test_text")
        );

        this.system = new MessagingSystem();

        assertEquals(0, this.system.size());

        for (Message message : this.messages) {
            this.system.add(message);
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddAlreadyExistingMessageShouldThrowException() {
        this.system.add(new TextMessage(11, "test_text"));
    }

    @Test
    public void testAddSingleShouldWorkCorrectly() {
        DataTransferSystem system = new MessagingSystem();

        assertEquals(0, system.size());

        system.add(new TextMessage(12, "test_text"));

        assertEquals(1, system.size());
    }

    @Test
    public void testAddMultipleShouldWorkCorrectly() {
        DataTransferSystem system = new MessagingSystem();

        assertEquals(0, system.size());

        for (Message message : messages) {
            system.add(message);
        }

        assertEquals(messages.size(), system.size());
    }

    @Test
    public void testAddMultipleShouldSetCorrectElements() {
        assertEquals(messages.size(), system.size());
        Message lightest = system.getLightest();
        assertNotNull(lightest);
        assertEquals(4, lightest.getWeight());
        Message heaviest = system.getHeaviest();
        assertNotNull(heaviest);
        assertEquals(19, heaviest.getWeight());
    }

    @Test
    public void testGetPostOrderShouldReturnCorrectSequence() {
        List<Message> postOrder = this.system.getPostOrder();

        int[] expected = {4, 8, 6, 17, 19, 11};
        assertNotNull(postOrder);
        assertEquals(expected.length, postOrder.size());

        for (int i = 0; i < messages.size(); i++) {
            assertEquals(expected[i], postOrder.get(i).getWeight());
        }
    }

    @Test
    public void testGetPostOrderShouldReturnEmptyListOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        List<Message> postOrder = messagingSystem.getPostOrder();

        assertNotNull(postOrder);
        assertEquals(0, postOrder.size());
    }

    @Test
    public void testGetInOrderShouldReturnCorrectSequence() {
        List<Message> inOrder = this.system.getInOrder();

        int[] expected = {4, 6, 8, 11, 17, 19};
        assertNotNull(inOrder);
        assertEquals(expected.length, inOrder.size());

        for (int i = 0; i < messages.size(); i++) {
            assertEquals(expected[i], inOrder.get(i).getWeight());
        }
    }

    @Test
    public void testGetInOrderShouldReturnEmptyListOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        List<Message> inOrder = messagingSystem.getInOrder();

        assertNotNull(inOrder);
        assertEquals(0, inOrder.size());
    }

    @Test
    public void testGetPreOrderShouldReturnCorrectSequence() {
        List<Message> preOrder = this.system.getPreOrder();

        int[] expected = {11, 6, 4, 8, 19, 17};
        assertNotNull(preOrder);
        assertEquals(expected.length, preOrder.size());

        for (int i = 0; i < messages.size(); i++) {
            assertEquals(expected[i], preOrder.get(i).getWeight());
        }
    }

    @Test
    public void testGetPreOrderShouldReturnEmptyListOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        List<Message> preOrder = messagingSystem.getPreOrder();

        assertNotNull(preOrder);
        assertEquals(0, preOrder.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetLightestShouldThrowExceptionWhenStorageIsEmpty() {
        MessagingSystem messagingSystem = new MessagingSystem();
        messagingSystem.getLightest();
    }

    @Test(expected = IllegalStateException.class)
    public void testGetHeaviestShouldThrowExceptionWhenStorageIsEmpty() {
        MessagingSystem messagingSystem = new MessagingSystem();
        messagingSystem.getHeaviest();
    }

    @Test
    public void testGetHeaviestShouldReturnCorrect() {
        Message heaviest = this.system.getHeaviest();
        assertNotNull(heaviest);
        assertEquals(19, heaviest.getWeight());
    }

    @Test
    public void testGetLightestShouldReturnCorrect() {
        Message lightest = this.system.getLightest();
        assertNotNull(lightest);
        assertEquals(4, lightest.getWeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByWeightShouldThrowExceptionIfNotPresent() {
        this.system.getByWeight(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByWeightShouldThrowExceptionIfStorageIsEmpty() {
        MessagingSystem messagingSystem = new MessagingSystem();
        messagingSystem.getByWeight(-1);
    }

    @Test
    public void testGetByWeightShouldReturnCorrect() {
        Message byWeight = this.system.getByWeight(6);
        assertNotNull(byWeight);
        assertEquals(6, byWeight.getWeight());
    }

    @Test
    public void testContainsShouldReturnTrueIfExists() {
        TextMessage textMessage = new TextMessage(11, "test_text");
        Boolean actual = this.system.contains(textMessage);
        assertTrue(actual);
    }

    @Test
    public void testContainsShouldReturnFalseIfNotExists() {
        TextMessage textMessage = new TextMessage(-1, "test_text");
        Boolean actual = this.system.contains(textMessage);
        assertFalse(actual);
    }

    @Test
    public void testContainsShouldReturnFalseOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        TextMessage textMessage = new TextMessage(-1, "test_text");
        Boolean actual = messagingSystem.contains(textMessage);
        assertFalse(actual);
    }

    @Test
    public void testSizeShouldReturnCorrectOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        int expectedSize = messagingSystem.size();
        assertEquals(0, expectedSize);
    }

    @Test
    public void testSizeShouldReturnCorrect() {
        int expectedSize = this.system.size();
        assertEquals(6, expectedSize);
    }

    @Test
    public void testSizeShouldReturnCorrectAfterInsert() {
        int actualSize = 6;
        TextMessage textMessage = new TextMessage(12, "text+message");
        this.system.add(textMessage);
        int expectedSize = this.system.size();
        assertEquals(actualSize + 1, expectedSize);
    }

    @Test
    public void testSizeShouldReturnCorrectAfterDeleteLightest() {
        int actualSize = 6;
        this.system.deleteLightest();
        int expectedSize = this.system.size();
        assertEquals(actualSize - 1, expectedSize);
    }

    @Test
    public void testSizeShouldReturnCorrectAfterDeleteHeaviest() {
        int actualSize = 6;
        this.system.deleteHeaviest();
        int expectedSize = this.system.size();
        assertEquals(actualSize - 1, expectedSize);
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteHeaviestShouldThrowExceptionOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        messagingSystem.deleteHeaviest();
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteLightestShouldThrowExceptionOnEmptyStorage() {
        MessagingSystem messagingSystem = new MessagingSystem();
        messagingSystem.deleteLightest();
    }

    @Test
    public void testDeleteHeaviestShouldReturnCorrectWhenStorageHasOnlyRoot() {
        MessagingSystem messagingSystem = new MessagingSystem();
        TextMessage textMessage = new TextMessage(11, "text-message");
        messagingSystem.add(textMessage);
        messagingSystem.deleteHeaviest();
//        assertNull(messagingSystem.getRoot());
        assertEquals(0, messagingSystem.size());
    }

    @Test()
    public void testDeleteLightestShouldReturnCorrectWhenStorageHasOnlyRoot() {
        MessagingSystem messagingSystem = new MessagingSystem();
        TextMessage textMessage = new TextMessage(11, "text-message");
        messagingSystem.add(textMessage);
        messagingSystem.deleteLightest();
//        assertNull(messagingSystem.getRoot());
        assertEquals(0, messagingSystem.size());
    }
}
