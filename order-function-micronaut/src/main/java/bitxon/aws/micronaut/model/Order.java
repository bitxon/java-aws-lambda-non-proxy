package bitxon.aws.micronaut.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable.Deserializable
public record Order(
    String name
) {}
