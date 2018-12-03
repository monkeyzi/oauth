package com.monkeyzi.oauth.im.listener;

import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;
import org.tio.utils.json.Json;

/**
 * ip监控
 */
@Slf4j
public class ImIpStatListener implements IpStatListener {
    public static final ImIpStatListener m=new ImIpStatListener();

    private ImIpStatListener(){

    }

    /**
     *
     * @param groupContext
     * @param ipStat
     */
    @Override
    public void onExpired(GroupContext groupContext, IpStat ipStat) {
        //在这里把统计数据入库中或日志
        log.info("ip数据={}",Json.toJson(ipStat));
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1, IpStat ipStat) throws Exception {

    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i, IpStat ipStat) throws Exception {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long l) throws Exception {

    }
}
