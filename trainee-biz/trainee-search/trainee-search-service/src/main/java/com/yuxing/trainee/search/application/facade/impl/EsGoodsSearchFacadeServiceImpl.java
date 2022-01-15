package com.yuxing.trainee.search.application.facade.impl;

import com.yuxing.trainee.common.core.Pager;
import com.yuxing.trainee.search.api.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.query.EsGoodsQuery;
import com.yuxing.trainee.search.application.assembler.EsGoodsAssembler;
import com.yuxing.trainee.search.application.facade.EsGoodsSearchFacadeService;
import com.yuxing.trainee.search.domain.entity.EsGoods;
import com.yuxing.trainee.search.domain.entity.EsGoodsSearchQuery;
import com.yuxing.trainee.search.domain.respository.EsGoodsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void saveDoc(SaveGoodsDocCommand command) {
        esGoodsRepository.save(new EsGoods(command.getId().toString(), command.getName(), command.getCode(), command.getType()));
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

    @Override
    public Pager<EsGoodsDTO> search(EsGoodsQuery query) {
        NativeSearchQuery searchQuery = new EsGoodsSearchQuery(query.getPosition(), query.getPageSize(), query.getKeyword(), query.getType()).getQuery();
        SearchHits<EsGoods> hits = elasticsearchRestTemplate.search(searchQuery, EsGoods.class);
        Pager<EsGoodsDTO> pager = new Pager<>(query.getPage(), query.getPageSize(), hits.getTotalHits());
        List<EsGoodsDTO> collect = hits.getSearchHits().stream().map(s -> EsGoodsAssembler.INSTANCE.toDto(s.getContent())).collect(Collectors.toList());
        pager.setData(collect);
        return pager;
    }
}