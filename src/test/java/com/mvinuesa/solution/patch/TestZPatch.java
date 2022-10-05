package com.mvinuesa.solution.patch;

import static com.flipkart.zjsonpatch.DiffFlags.OMIT_VALUE_ON_REMOVE;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.flipkart.zjsonpatch.DiffFlags;
import com.flipkart.zjsonpatch.JsonDiff;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestZPatch {

    @Test
    void testDiff() {
        var sourcePojo = new TestPatchPojo();
        sourcePojo.setName("Pepe");
        sourcePojo.setSurname("Perez");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.convertValue(sourcePojo, JsonNode.class);


        JsonNode target = mapper.convertValue(new TestPatchPojo(), JsonNode.class);


        EnumSet<DiffFlags> flags = DiffFlags.defaults();
        JsonNode patch = JsonDiff.asJson(source, target, flags);
        JsonNode viceversa = JsonDiff.asJson(target, source, flags);

        LOGGER.info("Patch: {}", patch);
        LOGGER.info("Vice versa: {}", viceversa);

    }


    @Test
    void testDiffWithMap() {
        var map = new HashMap<String, String>();
        map.put("name", "Pepe");
        map.put("surname", "Perez");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.convertValue(map, JsonNode.class);

        var map2 = new HashMap<String, String>();
        map2.put("name", "Fernando");
        map2.put("lastName", "Rodriguez");

        JsonNode target = mapper.convertValue(map2, JsonNode.class);

        EnumSet<DiffFlags> flags = EnumSet.of(OMIT_VALUE_ON_REMOVE);
        JsonNode patch = JsonDiff.asJson(source, target, flags);
        JsonNode viceversa = JsonDiff.asJson(target, source, flags);

        LOGGER.info("Patch: {}", patch);
        LOGGER.info("Vice versa: {}", viceversa);

        StreamSupport.stream(patch.spliterator(), false)
            .forEach(jsonNode ->
                LOGGER.info("element: {}", jsonNode)
            );

        JsonNode viceversaDeleteRemove = StreamSupport.stream(viceversa.spliterator(), false)
            .filter(e -> !"remove".equals(e.get("op").asText()))
            .collect(Collector.of(
                mapper::createArrayNode,
                ArrayNode::add,
                (result, toMerge) -> result.addAll(result)
            ));

        LOGGER.info("Vice versa DeleteRemove: {}", viceversaDeleteRemove);
        LOGGER.info("Vice versa:: {}", viceversa);

        Iterator<JsonNode> nodes = patch.elements();
        while(nodes.hasNext()) {
            var element = nodes.next();
            if(element.get("op").textValue().equals("remove")){
                nodes.remove();
            }
        }

        LOGGER.info("Patch: {}", patch);
    }

    @Data
    @NoArgsConstructor
    private static class TestPatchPojo {
        private String name;
        private String surname;
    }
}
