package bitxon.aws.micronaut.model;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable.Serializable
public record SavedOrder(
    UUID id,
    String name,
    String processedBy
) {}
