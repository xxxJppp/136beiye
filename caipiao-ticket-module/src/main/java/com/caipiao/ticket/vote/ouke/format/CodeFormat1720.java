package com.caipiao.ticket.vote.ouke.format;

import com.caipiao.common.constants.LotteryConstants;
import com.caipiao.domain.ticket.SchemeTicket;
import com.caipiao.plugin.Lottery1720;
import com.caipiao.plugin.helper.GamePluginAdapter;
import com.caipiao.plugin.helper.PluginUtil;
import com.caipiao.plugin.sturct.GameCastCode;
import com.caipiao.ticket.bean.CodeInfo;
import com.caipiao.ticket.util.OuKeTicketUtil;
import com.caipiao.ticket.vote.base.AbstractCodeFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 竞彩足球胜平负投注串转换
 * Created by kouyi on 2017/12/13.
 */
public class CodeFormat1720 extends AbstractCodeFormat {

    /**
     * 格式化投注信息为出票商格式
     * @param ticket
     * @param plugin
     * @return
     */
    public CodeInfo getCodeBean(SchemeTicket ticket, GamePluginAdapter plugin){
        CodeInfo cb = new CodeInfo();
        try {
            GameCastCode gcc = plugin.parseGameCastCode(ticket.getCodes());
            if(gcc.getCastMoney() * ticket.getMultiple() != ticket.getMoney().intValue()) {//判断金额是否一致，防止票表被修改
                cb.setErrorCode(3);
            } else {
                //SPF|20180131002=1,20180131003=0|2*1
                JSONArray array = new JSONArray();
                String[] codes = PluginUtil.splitter(ticket.getCodes(), SX_SPLIT);
                String[] matchs = PluginUtil.splitter(codes[1], DH_SPLIT);
                for(int x = 0; x < matchs.length; x++) {
                    String[] m = PluginUtil.splitter(matchs[x], DY_SPLIT);
                    JSONObject body = new JSONObject();
                    body.put("matchKey", "");
                    body.put("matchNumber", m[0]);
                    JSONObject sebody = new JSONObject();
                    StringBuffer buffer = new StringBuffer();
                    String[] choose = m[1].split("\\/");
                    for(int y=0; y<choose.length; y++) {
                        buffer.append(OuKeTicketUtil.getVoteChooseTypeMap(codes[0]+choose[y]));
                        if(y != choose.length -1) {
                            buffer.append(",");
                        }
                    }
                    sebody.put(OuKeTicketUtil.getLotteryTypeMap(LotteryConstants.jcWfPrefixPlayIdMaps.get(ticket.getLotteryId()+codes[0])), buffer.toString());
                    body.put("value", sebody);
                    array.add(body);
                }
                cb.setArray(array);
                cb.setPlayType(OuKeTicketUtil.getLotteryTypeMap(LotteryConstants.jcWfPrefixPlayIdMaps.get(ticket.getLotteryId()+codes[0])));
                cb.setOrderId(ticket.getTicketId());
                cb.setMoney(ticket.getMoney().intValue());
                cb.setMultiple(ticket.getMultiple());
                cb.setZhuShu(cb.getMoney()/cb.getMultiple()/2);
                cb.setPass(OuKeTicketUtil.getPassTypeMap(codes[2]));
            }
        } catch (Exception e) {
            logger.error("[欧克竞彩提票]-> 格式化票信息异常 票号=" + ticket.getTicketId(), e);
            cb.setErrorCode(3);
        }
        return cb;
    }

    public static void main(String[] args) throws Exception {
        String codes="CBF|20180201001=3:1/4:1,20180201003=1:1/2:2|2*1";
        Lottery1720 gcp = new Lottery1720();
        CodeFormat1720 gcc = new CodeFormat1720();
        SchemeTicket t = new SchemeTicket();
        t.setMoney(24d);
        t.setMultiple(3);
        t.setCodes(codes);
        t.setLotteryId("1700");
        CodeInfo bean=gcc.getCodeBean(t, gcp);
        System.out.println(bean.getArray().toString());
    }

}
