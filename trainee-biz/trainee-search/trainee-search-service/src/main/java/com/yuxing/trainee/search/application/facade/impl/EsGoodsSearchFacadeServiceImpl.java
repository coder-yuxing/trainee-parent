package com.yuxing.trainee.search.application.facade.impl;

import com.yuxing.trainee.search.api.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.dto.EsGoodsDTO;
import com.yuxing.trainee.search.application.assembler.EsGoodsAssembler;
import com.yuxing.trainee.search.application.facade.EsGoodsSearchFacadeService;
import com.yuxing.trainee.search.domain.entity.EsGoods;
import com.yuxing.trainee.search.domain.respository.EsGoodsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 素材搜索相关接口实现
 *
 * @author yuxing
 * @since 2022/1/14
 */
@Service
@AllArgsConstructor
public class EsGoodsSearchFacadeServiceImpl implements EsGoodsSearchFacadeService {

    private final EsGoodsRepository esGoodsRepository;

    @Override
    public void saveDoc(SaveGoodsDocCommand command) {
        esGoodsRepository.save(new EsGoods(command.getId().toString(), command.getName(), command.getCode()));
    }

    @Override
    public EsGoodsDTO getById(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return EsGoodsAssembler.INSTANCE.toDto(esGoodsRepository.findById(id.toString()).orElse(null));
    }

    @Override
    public void deletedById(Long id) {
        if (Objects.isNull(id)) {
            return;
        }
        esGoodsRepository.deleteById(id.toString());
    }
}
