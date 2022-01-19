package com.yuxing.trainee.search.application.facade.impl;

import com.yuxing.trainee.core.Pager;
import com.yuxing.trainee.search.api.goods.command.BatchSaveGoodsDocCommand;
import com.yuxing.trainee.search.api.goods.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.goods.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.goods.query.EsGoodsQuery;
import com.yuxing.trainee.search.application.assembler.EsGoodsAssembler;
import com.yuxing.trainee.search.application.facade.EsGoodsSearchFacadeService;
import com.yuxing.trainee.search.domain.entity.EsGoods;
import com.yuxing.trainee.search.domain.entity.EsGoodsSearchQuery;
import com.yuxing.trainee.search.domain.respository.EsGoodsRepository;
import com.yuxing.trainee.search.infrastructure.util.ElasticsearchUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
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
        esGoodsRepository.save(EsGoodsAssembler.INSTANCE.toDo(command));
    }

    @Override
    public void batchSaveDocs(BatchSaveGoodsDocCommand command) {
        esGoodsRepository.saveAll(EsGoodsAssembler.INSTANCE.toDoList(command.getItems()));
    }

    @Override
    public EsGoodsDTO getById(Long id) {
        return EsGoodsAssembler.INSTANCE.toDto(esGoodsRepository.findById(id.toString()).orElse(null));
    }

    @Override
    public void deletedById(Long id) {
        esGoodsRepository.deleteById(id.toString());
    }

    @Override
    public Pager<EsGoodsDTO> search(EsGoodsQuery query) {
        EsGoodsSearchQuery searchQuery = EsGoodsAssembler.INSTANCE.toQuery(query);
        SearchHits<EsGoods> hits = elasticsearchRestTemplate.search(searchQuery.getQuery(), EsGoods.class);
        Pager<EsGoodsDTO> pager = new Pager<>(query.getPage(), query.getPageSize(), hits.getTotalHits());
        List<EsGoodsDTO> collect = hits.getSearchHits().stream().map(s -> EsGoodsAssembler.INSTANCE.toDto(s.getContent())).collect(Collectors.toList());
        pager.setData(collect);
        pager.setAggregations(ElasticsearchUtils.parseAggregations(hits.getAggregations()));
        return pager;
    }
}
