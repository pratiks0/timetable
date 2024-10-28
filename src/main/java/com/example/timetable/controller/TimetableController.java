package com.example.timetable.controller;

import com.example.timetable.model.Timetable;
import com.example.timetable.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private TimetableRepository timetableRepository;

    @GetMapping
    public List<Timetable> getAllTimetableEntries() {
        return timetableRepository.findAll();
    }

    @PostMapping
    public Timetable createTimetableEntry(@RequestBody Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timetable> getTimetableEntryById(@PathVariable Long id) {
        return timetableRepository.findById(id)
                .map(timetable -> ResponseEntity.ok().body(timetable))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Timetable> updateTimetableEntry(@PathVariable Long id, @RequestBody Timetable timetableDetails) {
        return timetableRepository.findById(id).map(timetable -> {
            timetable.setSubject(timetableDetails.getSubject());
            timetable.setDay(timetableDetails.getDay());
            timetable.setStartTime(timetableDetails.getStartTime());
            timetable.setEndTime(timetableDetails.getEndTime());
            Timetable updatedTimetable = timetableRepository.save(timetable);
            return ResponseEntity.ok(updatedTimetable);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Timetable> patchTimetableEntry(@PathVariable Long id, @RequestBody Timetable timetableDetails) {
        return timetableRepository.findById(id).map(timetable -> {
            if (timetableDetails.getSubject() != null) {
                timetable.setSubject(timetableDetails.getSubject());
            }
            if (timetableDetails.getDay() != null) {
                timetable.setDay(timetableDetails.getDay());
            }
            if (timetableDetails.getStartTime() != null) {
                timetable.setStartTime(timetableDetails.getStartTime());
            }
            if (timetableDetails.getEndTime() != null) {
                timetable.setEndTime(timetableDetails.getEndTime());
            }
            Timetable updatedTimetable = timetableRepository.save(timetable);
            return ResponseEntity.ok(updatedTimetable);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTimetableEntry(@PathVariable Long id) {
        return timetableRepository.findById(id).map(timetable -> {
            timetableRepository.delete(timetable);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
