package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Profile;
import com.enigma.nutrismartbe.service.impl.ProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileServiceImpl profileController;

    @GetMapping("/profile/{id}")
    public Profile getProfile(@PathVariable String id) {
        return profileController.getProfile(id);
    }

    @PostMapping("/profile/form")
    public Profile saveProfile(@RequestBody Profile profile){
        return profileController.saveProfile(profile);
    }

//    @DeleteMapping("/{id}")
//    public void removeProfile(@PathVariable String id){
//        profileController.removeProfile(id);
//    }

    @GetMapping("/admin/profile")
    public Page<Profile> getAllProfiles(@RequestParam(name = "page") Integer page,@RequestParam(name = "size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return profileController.getAllProfiles(pageable);
    }
}
