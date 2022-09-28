package core;

import model.ScheduledTask;
import model.Task;
import org.junit.Before;
import org.junit.Test;
import shared.Scheduler;

import java.util.List;

import static org.junit.Assert.*;

public class ProcessSchedulerTest {
    private Scheduler scheduler;

    @Before
    public void setUp() {
        this.scheduler = new ProcessScheduler();
        for (int i = 1; i <= 20; i++) {
            this.scheduler.add(new ScheduledTask(i, "test_description"));
        }
    }

    @Test
    public void testPeekOnSingleElement() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(100, "test_description"));
        Task task = scheduler.peek();
        assertNotNull(task);
        assertEquals(100, task.getId());
    }

    @Test
    public void testPeekOnMultipleElement() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(73, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        scheduler.add(new ScheduledTask(100, "test_description"));
        Task task = scheduler.peek();
        assertNotNull(task);
        assertEquals(73, task.getId());
    }

    @Test
    public void testAddOnMultipleElement() {
        Task task = this.scheduler.peek();
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals(20, this.scheduler.size());

        int expectedId = 1;
        while (this.scheduler.size() > 0) {
            Task process = this.scheduler.process();
            assertNotNull(process);
            assertEquals(expectedId++, process.getId());
        }
        assertEquals(21, expectedId);
    }

    @Test
    public void testAddOnSingleElement() {
        Scheduler scheduler = new ProcessScheduler();
        assertNull(scheduler.peek());
        assertEquals(0, scheduler.size());

        scheduler.add(new ScheduledTask(1, "test_description"));

        assertNotNull(scheduler.peek());
        assertEquals(1, scheduler.peek().getId());
        assertEquals(1, scheduler.size());
    }

    @Test
    public void testPeekShouldReturnCorrectAndShouldNotRemove() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(42, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        Task task = scheduler.peek();
        assertNotNull(task);
        assertEquals(42, task.getId());
        assertEquals(21, scheduler.size());
        task = scheduler.peek();
        assertNotNull(task);
        assertEquals(42, task.getId());
        assertEquals(21, scheduler.size());
    }

    @Test
    public void testProcessShouldReturnCorrectAndShouldRemove() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(42, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        Task task = scheduler.process();
        assertNotNull(task);
        assertEquals(42, task.getId());
        assertEquals(20, scheduler.size());
        task = scheduler.process();
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals(19, scheduler.size());
    }

    @Test
    public void testInsertBeforeShouldReturnCorrect() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(42, "test_description"));
        for (int i = 1; i <= 20; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        scheduler.insertBefore(42, new ScheduledTask(100, "test_description"));
        assertEquals(22, scheduler.size());
        assertEquals(100, scheduler.peek().getId());
    }

    @Test
    public void testInsertAfterShouldReturnCorrect() {
        Scheduler scheduler = new ProcessScheduler();
        scheduler.add(new ScheduledTask(42, "test_description"));
        for (int i = 1; i <= 10; i++) {
            scheduler.add(new ScheduledTask(i, "test_description"));
        }
        scheduler.insertAfter(10, new ScheduledTask(100, "test_description"));
        assertEquals(12, scheduler.size());
        int[] expected = {42, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100};
        List<Task> tasks = scheduler.toList();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tasks.get(i).getId());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertAfterShouldThrowExceptionWhenIdNotFoundInStorage() {
        scheduler.insertAfter(99, new ScheduledTask(100, "test_description"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertBeforeShouldThrowExceptionWhenIdNotFoundInStorage() {
        scheduler.insertBefore(99, new ScheduledTask(100, "test_description"));
    }

    @Test
    public void testContainsShouldReturnFalseOnEmptyStorage() {
        Scheduler scheduler = new ProcessScheduler();
        ScheduledTask task = new ScheduledTask(42, "test_description");
        Boolean actual = scheduler.contains(task);
        assertFalse(actual);
    }

    @Test
    public void testContainsShouldReturnFalseWhenTaskDoesNotExists() {
        ScheduledTask task = new ScheduledTask(42, "test_description");
        Boolean actual = scheduler.contains(task);
        assertFalse(actual);
    }

    @Test
    public void testContainsShouldReturnTrueWhenTaskExists() {
        ScheduledTask task = new ScheduledTask(20, "test_description");
        Boolean actual = scheduler.contains(task);
        assertTrue(actual);
    }

    @Test
    public void testSizeShouldReturnCorrect() {
        assertEquals(20, scheduler.size());
        scheduler.add(new ScheduledTask(21, ""));
        assertEquals(21, scheduler.size());
        scheduler.clear();
        assertEquals(0, scheduler.size());
    }

    @Test
    public void testSizeShouldReturnCorrectOnEmptyStorage() {
        ProcessScheduler processScheduler = new ProcessScheduler();
        int actual = processScheduler.size();
        assertEquals(0, actual);
    }

    @Test
    public void testRemoveTaskShouldReturnTrue() {
        ScheduledTask task = new ScheduledTask(20, "test_description");
        Boolean actual = scheduler.remove(task);
        assertTrue(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveTaskShouldThrowException() {
        ScheduledTask task = new ScheduledTask(21, "test_description");
        scheduler.remove(task);
    }

    @Test
    public void testRemoveTaskByIdShouldReturnTrue() {
        int idToRemove = 20;
        Boolean actual = scheduler.remove(idToRemove);
        assertTrue(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveTaskByIdShouldThrowException() {
        int idToRemove = 100;
        scheduler.remove(idToRemove);
    }

    @Test
    public void testClearShouldClearStorageCorrectly() {
        assertEquals(20, scheduler.size());
        scheduler.clear();
        assertEquals(0, scheduler.size());
    }

    @Test
    public void testToArrayShouldReturnCorrect() {
        Task[] tasks = scheduler.toArray();
        assertNotNull(tasks);
        assertEquals(tasks.length, scheduler.size());
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tasks[i].getId());
            assertEquals("test_description", tasks[i].getDescription());
        }
    }

    @Test
    public void testToArrayShouldReturnCorrectOnEmptyStorage() {
        ProcessScheduler processScheduler = new ProcessScheduler();
        Task[] tasks = processScheduler.toArray();
        assertNotNull(tasks);
        assertEquals(tasks.length, processScheduler.size());
    }

    @Test
    public void testToListShouldReturnCorrect() {
        List<Task> tasks = scheduler.toList();
        assertNotNull(tasks);
        assertEquals(tasks.size(), scheduler.size());
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], tasks.get(i).getId());
            assertEquals("test_description", tasks.get(i).getDescription());
        }
    }

    @Test
    public void testToListShouldReturnCorrectOnEmptyStorage() {
        ProcessScheduler processScheduler = new ProcessScheduler();
        List<Task> tasks = processScheduler.toList();
        assertNotNull(tasks);
        assertEquals(tasks.size(), processScheduler.size());
        assertEquals(0, tasks.size());
    }

    @Test
    public void testReverseShouldReturnCorrect() {
        scheduler.reverse();
        assertEquals(20, scheduler.size());
        assertEquals(20, scheduler.peek().getId());
        int[] expected = {20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int current : expected) {
            assertEquals(current, scheduler.process().getId());
        }
    }

    @Test
    public void testFindTaskByIdShouldReturnCorrect() {
        ScheduledTask task = new ScheduledTask(20, "test_description");
        int idToFind = 20;
        Task actual = scheduler.find(idToFind);
        assertNotNull(actual);
        assertEquals(actual, task);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTaskByIdShouldThrowException() {
        int idToFind = 100;
        scheduler.find(idToFind);
    }

    @Test
    public void testFindTaskShouldReturnCorrect() {
        ScheduledTask task = new ScheduledTask(20, "test_description");
        Task actual = scheduler.find(task);
        assertNotNull(actual);
        assertEquals(actual, task);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTaskShouldThrowException() {
        ScheduledTask task = new ScheduledTask(21, "test_description");
        scheduler.find(task);
    }

    @Test
    public void testRescheduleShouldReturnCorrect() {
        ScheduledTask first = new ScheduledTask(20, "test_description");
        ScheduledTask second = new ScheduledTask(1, "test_description");
        assertEquals(20, scheduler.size());
        scheduler.reschedule(first, second);
        assertEquals(20, scheduler.size());
        int[] expected = {20, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 1};
        for (int current : expected) {
            assertEquals(current, scheduler.process().getId());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTaskShouldThrowExceptionWhenFirstTaskDoesNotExists() {
        ScheduledTask first = new ScheduledTask(100, "test_description");
        ScheduledTask second = new ScheduledTask(20, "test_description");
        scheduler.reschedule(first, second);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTaskShouldThrowExceptionWhenSecondTaskDoesNotExists() {
        ScheduledTask first = new ScheduledTask(20, "test_description");
        ScheduledTask second = new ScheduledTask(100, "test_description");
        scheduler.reschedule(first, second);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTaskShouldThrowExceptionWhenBothTasksDoesNotExists() {
        ScheduledTask first = new ScheduledTask(99, "test_description");
        ScheduledTask second = new ScheduledTask(100, "test_description");
        scheduler.reschedule(first, second);
    }
}