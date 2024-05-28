package org.cyk.album.service.impl

import org.cyk.album.facade.*
import org.cyk.album.repo.es.AlbumDocRepo
import org.cyk.album.repo.es.impl.AlbumDoc
import org.cyk.album.repo.mongo.AlbumCollectRepo
import org.cyk.album.repo.mongo.AlbumLikeRepo
import org.cyk.album.repo.mongo.AlbumStatRepo
import org.cyk.album.repo.mongo.impl.AlbumStat
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.repo.mysql.AlbumPhotoRepo
import org.cyk.album.repo.mysql.mapper.AlbumInfo
import org.cyk.album.repo.mysql.mapper.AlbumPhoto
import org.cyk.album.service.AlbumSearchService
import org.cyk.album.service.cmd.AlbumSearchCmd
import org.cyk.album.service.rpc.UserInfoServiceRpcImpl
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.cyk.feign.user.UserInfoDto
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class AlbumSearchServiceImpl: AlbumSearchService {

    @Resource private lateinit var cmd: AlbumSearchCmd

    override fun pageAlbumVo(o: AlbumSearchDto): PageResp<AlbumVo> {
        return cmd.pageAlbumVo(o)
    }

    override fun suggest(o: AlbumSuggestDto): List<AlbumSuggestVo> {
        return cmd.suggest(o)
    }

}