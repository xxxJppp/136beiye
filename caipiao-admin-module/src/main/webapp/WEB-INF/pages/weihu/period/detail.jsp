<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="card-header">
    <div class="planflom-header">
        <div class="card-header-title clearfix">
            <button type="button" class="card-close plus-icon p-guanbi"></button>
            <div class="pull-left">
                <h1 class="entry-name">
                    <span class="plus-icon"></span>
                    <span>期次详细</span>
                </h1>
            </div>
            <div class="btn-list pull-right clearfix" style="margin-top: 6px">
                <a href="javascript:;" class="card-edit" data-toggle="modal" data-target="#dt_peratorModal" onclick="dt_editPeriod('${params.id}')"><i class="plus-icon p-edit"></i>编辑</a>
            </div>
        </div>
        <div class="card-header-con">
            <ul class="abstract-list clearfix">
                <li class="abstract-item">
                    <p class="abstract-label">彩种名称</p>
                    <div class="abstract-value">
                        <span>${params.lotteryName}</span>
                    </div>
                </li>
                <li class="abstract-item">
                    <p class="abstract-label">期次号</p>
                    <div class="abstract-value">
                        <span>${params.period}</span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="card-main">
    <ul class="mian-detail clearfix">
        <li class="information-basic">
            <div class="white-box">
                <div class="card-tab-list">
                    <ul class="card-tab-nav clearfix">
                        <li class="card-tab-item active">
                            <a href="javascript:;">基本信息</a>
                        </li>
                    </ul>
                </div>
                <div class="card-tab-con active">待填充</div>
            </div>
        </li>
        <li class="card-record">
            <div class="white-box">
                <div class="card-tab-list">
                    <ul class="card-tab-nav clearfix">
                        <li class="card-tab-item active">
                            <a href="javascript:;">其它信息</a>
                        </li>
                    </ul>
                </div>
                <div class="card-tab-con active">
                    <div class="tab-con-details">
                        待填充
                    </div>
                </div>
            </div>
        </li>
        <li class="related-information">
            <div class="white-box">
                <div class="card-tab-list">
                    <ul class="card-tab-nav clearfix">
                        <li class="card-tab-item active">
                            <a href="javascript:;">相关信息</a>
                        </li>
                    </ul>
                </div>
                <div class="card-tab-con active">
                    <div class="tab-con-details">
                        待填充
                    </div>
                </div>
            </div>
        </li>
    </ul>
</div>
<div class="card-wrap" id="dt_peratorModal"></div>
<script>
//点击编辑期次
function dt_editPeriod(id)
{
    $('#dt_peratorModal').fillWithUrl('${pageContext.request.contextPath}/weihu/period/initEdit?id=' + id);
}
</script>