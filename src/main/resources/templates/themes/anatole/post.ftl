<#include "module/macro.ftl">
<@head title="${post.postTitle} · ${options.blog_title?default('Anatole')}" keywords="${post.postTitle},${options.seo_keywords?default('Anatole')},${tagWords}" description="${post.postSummary?if_exists}"></@head>
<#include "module/sidebar.ftl">
<div class="main">
    <link href="/anatole/source/plugins/prism/prism.css" type="text/css" rel="stylesheet" />
    <style>
        code, tt {
            font-size: 1.2em;
        }
        table {
            border-spacing: 0;
            border-collapse: collapse;
            margin-top: 0;
            margin-bottom: 16px;
            display: block;
            width: 100%;
            overflow: auto;

        }
        table th {
            font-weight: 600;
        }
        table th,
        table td {
            padding: 6px 13px;
            border: 1px solid #dfe2e5;
        }
        table tr {
            background-color: #fff;
            border-top: 1px solid #c6cbd1;
        }
        table tr:nth-child(2n) {
            background-color: #f6f8fa;
        }
    </style>
    <#include "module/page-top.ftl">
    <div class="autopagerize_page_element">
        <div class="content">
            <div class="post-page">
                <div class="post animated fadeInDown">
                    <div class="post-title">
                        <h3>
                            <a>${post.postTitle}</a>
                        </h3>
                    </div>
                    <div class="post-content">
                        ${post.postContent?if_exists}
                    </div>
                    <div class="post-footer">
                        <div class="meta">
                            <div class="info">
                                <i class="fa fa-sun-o"></i>
                                <span class="date">${post.postDate?string("yyyy-MM-dd")}</span>
                                <i class="fa fa-comment-o"></i>
                                <a href="/archives/${post.postUrl}#comment_widget">Comments</a>
                                <#if post.tags?size gt 0>
                                    <i class="fa fa-tag"></i>
                                    <#list post.tags as tag>
                                        <a href="/tags/${tag.tagUrl}" class="tag">&nbsp;${tag.tagName}</a>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pagination">
                    <ul class="clearfix">
                        <#if afterPost??>
                        <li class="pre pagbuttons">
                            <a class="btn" role="navigation" href="/archives/${afterPost.postUrl}" title="${afterPost.postTitle}">上一篇</a>
                        </li>
                        </#if>
                        <#if beforePost??>
                        <li class="next pagbuttons">
                            <a class="btn" role="navigation" href="/archives/${beforePost.postUrl}" title="${beforePost.postTitle}">下一篇</a>
                        </li>
                        </#if>
                    </ul>
                </div>
                <div id="comment_widget">
                    <#include "module/comment.ftl">
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/anatole/source/plugins/prism/prism.js"></script>
<@footer></@footer>
