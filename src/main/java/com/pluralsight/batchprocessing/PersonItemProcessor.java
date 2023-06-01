package com.pluralsight.batchprocessing;

import com.pluralsight.batchprocessing.model.InputPerson;
import com.pluralsight.batchprocessing.model.OutputPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PersonItemProcessor implements ItemProcessor<InputPerson, OutputPerson> {
  private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

  @Override
  public OutputPerson process(final InputPerson person) {
    final String firstName = person.getFirstName().toUpperCase();
    final String lastName = person.getLastName().toUpperCase();
    final int age = person.getAge();

    final String fullName = firstName + " " + lastName;

    final OutputPerson transformedPerson = new OutputPerson(fullName, age);

    log.info("Converting (" + person + ") into (" + transformedPerson + ")");

    return transformedPerson;
  }
}
