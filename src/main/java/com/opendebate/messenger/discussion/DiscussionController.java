package com.opendebate.messenger.discussion;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscussionController {

    @PostMapping(value = "discussions", produces="application/json", consumes="application/json")
    public Discussion helloWorld(@RequestBody Discussion discussion) {
        return discussion;
    }
}
