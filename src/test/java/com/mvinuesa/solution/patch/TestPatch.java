package com.mvinuesa.solution.patch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import java.util.HashMap;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestPatch {

    @Test
    void testDiff() {
        var sourcePojo = new TestPatchPojo();
        sourcePojo.setName("Pepe");
        sourcePojo.setSurname("Perez");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.convertValue(sourcePojo, JsonNode.class);


        JsonNode target = mapper.convertValue(new TestPatchPojo(), JsonNode.class);

        final JsonPatch patch = JsonDiff.asJsonPatch(source, target);
        final JsonPatch viceversa = JsonDiff.asJsonPatch(target, source);
        final JsonNode patchNode = JsonDiff.asJson(source, target);

        LOGGER.info("Patch: {}", patch);
        LOGGER.info("Vice versa: {}", viceversa);

        LOGGER.info("Node: {}", patchNode);
    }


    @Test
    void testDiffWithMap() {
        var map = new HashMap<String, String>();
        map.put("name", "Pepe");
        map.put("surname", "Perez");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.convertValue(map, JsonNode.class);

        var map2 = new HashMap<String, String>();
        map.put("name", "Fernando");
        map.put("lastName", "Rodriguez");

        JsonNode target = mapper.convertValue(map2, JsonNode.class);

        final JsonPatch patch = JsonDiff.asJsonPatch(source, target);
        final JsonPatch viceversa = JsonDiff.asJsonPatch(target, source);
        final JsonNode patchNode = JsonDiff.asJson(source, target);

        LOGGER.info("Patch: {}", patch);
        LOGGER.info("Vice versa: {}", viceversa);

        LOGGER.info("Node: {}", patchNode);
    }

    @Data
    @NoArgsConstructor
    private static class TestPatchPojo {
        private String name;
        private String surname;
    }
}
