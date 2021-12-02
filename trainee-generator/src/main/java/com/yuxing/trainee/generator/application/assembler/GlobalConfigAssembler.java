package com.yuxing.trainee.generator.application.assembler;


import com.yuxing.trainee.generator.application.command.GenerateMapperFileCommand;
import com.yuxing.trainee.generator.domain.valueobject.config.GlobalConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GlobalConfigAssembler {

    GlobalConfigAssembler INSTANCE = Mappers.getMapper(GlobalConfigAssembler.class);

    GlobalConfig toConfig(GenerateMapperFileCommand command);
}
