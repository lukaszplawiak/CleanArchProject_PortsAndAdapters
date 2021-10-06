package com.lukaszplawiak.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Set;
// to jest interfejs ze schowaną swoją implementecją, dotpęp do implementacji mamy tylko za posrednictwm interfejsu. Nikt się do implementacji nie dostanie bo mamy tylko trzy pola w ProjectDto a jacson(ObjectMapper) dzięki adnotacji spokojnie będzie sobie serializował i desrrializował
@JsonDeserialize(as = ProjectDto.DeserializationImpl.class)
public interface ProjectDto {  // to jest interfejs z którego ObjectMapper(jason) niejest w stanie zrobnic deserializacji

    static ProjectDto create(final int id, final String name, final Set<ProjectStepDto> steps){
        return new DeserializationImpl(id, name, steps);
    }

    int getId();

    String getName();

    Set<ProjectStepDto> getSteps();

    class DeserializationImpl implements ProjectDto { // wiec robimy taka sztuczke i robimy klase wewnetrzna ktora implementuje nadsz interfejs ProjectDto i wszystko dzial

        private int id;
        private String name;
        private Set<ProjectStepDto> steps;

        DeserializationImpl(final int id, final String name, final Set<ProjectStepDto> steps) {
            this.id = id;
            this.name = name;
            this.steps = steps;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Set<ProjectStepDto> getSteps() {
            return steps;
        }
    }
}
