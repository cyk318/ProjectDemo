package org.cyk.album.service.impl.strategy

import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.infra.type.AlbumState
import org.cyk.album.service.cmd.AlbumCmd
import org.cyk.album.service.message.AlbumInfoPublisher
import org.cyk.base.utils.MyTimer

/**
 * 文章发布策略模式
 * @author 陈亦康
 */
interface AlbumPubStrategy {

    fun publish(d: AlbumPubDto): Int

}

class NormalAlbumPubStrategy(
    private val cmd: AlbumCmd,
): AlbumPubStrategy {

    override fun publish(d: AlbumPubDto): Int {
        d.state = AlbumState.NORMAL.code
        return cmd.saveAlbum(d)
    }

}

class PrivateAlbumPubStrategy(
    private val cmd: AlbumCmd,
): AlbumPubStrategy {

    override fun publish(d: AlbumPubDto): Int {
        d.state = AlbumState.PRIVATE.code
        return cmd.saveAlbum(d)
    }

}

class DraftAlbumPubStrategy(
    private val cmd: AlbumCmd,
): AlbumPubStrategy {

    override fun publish(d: AlbumPubDto): Int {
        d.state = AlbumState.DRAFT.code
        return cmd.saveAlbum(d)
    }

}

class TimeAlbumPubStrategy(
    val cmd: AlbumCmd,
    private val infoPublisher: AlbumInfoPublisher,
): AlbumPubStrategy {

    private val timer = MyTimer()

    override fun publish(d: AlbumPubDto): Int {
        d.state = AlbumState.NORMAL.code
        timer.schedule({
            cmd.saveAlbum(d)
            infoPublisher.addAlbumInfoEvent(d) }, d.pubTime!!.toLong())
        return 1
    }

}

