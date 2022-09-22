package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Task;
import com.example.demo.model.Tracker;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TrackerRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TrackerRepo trackerRepo;

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void updateTask(Long id, Task updatedTask, User user) {
        Task task = taskRepository.getOne(id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        task.setDate(updatedTask.getDate());

        Tracker tracker = new Tracker();
        tracker.setUser(user);

        if ((updatedTask.getDate().compareTo(LocalDate.now())) == 0) {
            tracker.setDate(updatedTask.getDate());
            tracker.setTime(updatedTask.getTimeSpent());
            tracker.setTask(task);
            task.setTimeSpent(task.getTimeSpent() + updatedTask.getTimeSpent());
        } else {
            task.setTimeSpent(updatedTask.getTimeSpent());
        }
        trackerRepo.save(tracker);

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByOwnerOrderByDateDesc(User user) {
        return taskRepository.findByOwnerOrderByDateDesc(user);
    }

    @Override
    public void setTaskCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(true);
        taskRepository.save(task);
    }

    @Override
    public void setTaskNotCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(false);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findFreeTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getOwner() == null && !task.isCompleted())
                .collect(Collectors.toList());

    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void assignTaskToUser(Task task, User user) {
        task.setOwner(user);
        taskRepository.save(task);
    }

    @Override
    public void unassignTask(Task task) {
        task.setOwner(null);
        taskRepository.save(task);
    }

}
