package io.github.arthurcech.orderscrudcommerce.mapper;

import io.github.arthurcech.orderscrudcommerce.dto.client.ClientResponse;
import io.github.arthurcech.orderscrudcommerce.dto.client.RegisterClientPayload;
import io.github.arthurcech.orderscrudcommerce.dto.client.UpdateClientPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toClient(RegisterClientPayload payload);

    ClientResponse toClientResponse(Client client);

    void updateClientFromPayload(UpdateClientPayload payload, @MappingTarget Client client);

}
