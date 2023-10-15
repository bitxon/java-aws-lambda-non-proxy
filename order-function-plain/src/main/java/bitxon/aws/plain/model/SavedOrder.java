package bitxon.aws.plain.model;

import java.util.UUID;

public record SavedOrder(
    UUID id,
    String name,
    String processedBy
) {}
