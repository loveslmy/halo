<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Halo Install</title>
    <link rel="stylesheet" href="/static/plugins/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/plugins/admin-lte/2.4.5/AdminLTE.min.css">
    <link rel="stylesheet" href="/static/plugins/animate/3.5.1/animate.min.css">
    <link rel="stylesheet" href="/static/plugins/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.js"></script>
    <![endif]-->
    <style>
        body {
            background-color: #f5f5f5;
        }

        .container {
            max-width: 850px;
        }

        .form-horizontal .control-label {
            text-align: left;
        }

        .logo {
            font-size: 56px;
            text-align: center;
            margin-bottom: 25px;
            font-weight: 500;
            color: #444;
            text-shadow: #b2baba .1em .1em .2em;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row" style="padding-top: 50px">
        <div class="col-lg-12 col-xs-12">
            <div class="logo animated fadeInUp">
                Halo
                <small style="font-size: 14px;">Install</small>
            </div>
            <#if !installed>
                <form method="post" action="/install/do" class="form-horizontal" id="installForm">
                    <div class="box box-solid animated" id="installFirst">
                        <div class="box-body" style="padding: 30px;">
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.1s">
                                <label for="blogTitle" class="col-sm-4 control-label">Blog标题：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="blogTitle" name="blogTitle" code="">
                                </div>
                            </div>
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.1s">
                                <label for="blogUrl" class="col-sm-4 control-label">BlogUrl：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="blogUrl" name="blogUrl" code="">
                                </div>
                            </div>
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.2s">
                                <label for="userEmail" class="col-sm-4 control-label">Email：
                                    <span data-toggle="tooltip" data-placement="top" title="重要，用于找回密码！"
                                          style="cursor: pointer">
                                    <i class="fa fa-question-circle" aria-hidden="true"></i>
                                    </span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="userEmail" name="userEmail"
                                           code="">
                                </div>
                            </div>
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.3s">
                                <label for="userName" class="col-sm-4 control-label">用户名：
                                    <span data-toggle="tooltip" data-placement="top" title="用于登录管理控制台"
                                          style="cursor: pointer">
                                    <i class="fa fa-question-circle" aria-hidden="true"></i>
                                    </span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="userName" name="userName" code="">
                                </div>
                            </div>
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.4s">
                                <label for="userDisplayName" class="col-sm-4 control-label">昵称：
                                    <span data-toggle="tooltip" data-placement="top" title="显示名称"
                                          style="cursor: pointer">
                                    <i class="fa fa-question-circle" aria-hidden="true"></i>
                                    </span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="userDisplayName"
                                           name="userDisplayName" code="">
                                </div>
                            </div>
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.5s">
                                <label for="userPwd" class="col-sm-4 control-label">登录密码：</label>
                                <div class="col-sm-8">
                                    <input type="password" class="form-control" id="userPwd" name="userPwd"
                                           code="">
                                </div>
                            </div>
                            <div class="form-group animated fadeInUp" style="animation-delay: 0.6s">
                                <label for="userRePwd" class="col-sm-4 control-label">确认密码：</label>
                                <div class="col-sm-8">
                                    <input type="password" class="form-control" id="userRePwd" name="userRePwd"
                                           code="">
                                </div>
                            </div>
                        </div>
                        <div class="box-footer" style="padding-right: 30px;">
                            <button type="submit"
                                    class="btn btn-primary btn-sm btn-flat pull-right animated fadeInUp"
                                    style="animation-delay: 0.9s">确认并安装
                            </button>
                        </div>
                    </div>
                    <div class="box box-solid animated fadeInUp" style="display: none" id="installSuccess">
                        <div class="box-body">
                            <h2>安装成功！</h2>
                            <h4>您可以选择进入首页或者登录管理控制台！</h4>
                        </div>
                        <div class="box-footer" style="padding-right: 30px;">
                            <a class="btn btn-primary btn-sm btn-flat" href="/">首页</a>
                            <a class="btn btn-primary btn-sm btn-flat" href="/admin/login">管理控制台</a>
                        </div>
                    </div>
                    <div class="box box-solid animated fadeInUp" style="display: none" id="installError">
                        <div class="box-body">
                            <h2>安装失败！</h2>
                            <h4>请尝试重新安装！</h4>
                        </div>
                        <div class="box-footer" style="padding-right: 30px;">
                            <a class="btn btn-primary btn-sm btn-flat" href="/install">返回</a>
                        </div>
                    </div>
                </form>
            <#else>
                <div class="animated fadeInUp" style="animation-delay: 0.1s">
                    <h4>已经安装成功，不能重复安装！</h4>
                </div>
            </#if>
        </div>
    </div>
</div>
</body>
<script src="/static/plugins/jquery/3.2.1/jquery.min.js"></script>
<script src="/static/plugins/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="/static/plugins/bootstrap-validator/0.5.3/js/bootstrapValidator.min.js"></script>
<script src="/static/plugins/bootstrap-validator/0.5.3/js/language/zh_CN.js"></script>
    <#if installed==false>
    <script>
        var domain = window.location.host;
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        });
        $(document).ready(function () {
            $('#blogUrl').val("http://" + domain);
            $('#installForm')
                    .bootstrapValidator({
                        message: '安装表单验证失败',
                        feedbackIcons: {
                            valid: 'glyphicon glyphicon-ok',
                            invalid: 'glyphicon glyphicon-remove',
                            validating: 'glyphicon glyphicon-refresh'
                        },
                        fields: {
                            blogTitle: {
                                message: 'BLog标题验证失败',
                                validators: {
                                    notEmpty: {
                                        message: 'Blog标题不能为空'
                                    }
                                }
                            },
                            userEmail: {
                                message: 'Email验证失败',
                                validators: {
                                    notEmpty: {
                                        message: 'Email不能为空'
                                    },
                                    emailAddress: {
                                        message: 'Email格式有误'
                                    }
                                }
                            },
                            userName: {
                                message: '用户名验证失败',
                                validators: {
                                    notEmpty: {
                                        message: '用户名不能为空'
                                    },
                                    stringLength: {
                                        min: 1,
                                        max: 16,
                                        message: '用户名超出长度限制'
                                    }
                                }
                            },
                            userPwd: {
                                message: '密码验证失败',
                                validators: {
                                    notEmpty: {
                                        message: '密码不能为空'
                                    },
                                    stringLength: {
                                        min: 6,
                                        max: 18,
                                        message: '密码长度必须在6到18位之间'
                                    }
                                }
                            },
                            userRePwd: {
                                message: '密码验证失败',
                                validators: {
                                    notEmpty: {
                                        message: '确认密码不能为空'
                                    },
                                    identical: {
                                        field: 'userPwd',
                                        message: '两次输入的密码不相符'
                                    }
                                }
                            }
                        }
                    })
                    .on('success.form.bv', function (e) {
                        e.preventDefault();
                        var $form = $(e.target);
                        $.post($form.attr('action'), $form.serialize(), function (datas) {
                            if (result === true) {
                                $('#installFirst').hide();
                                $('#installSuccess').show();
                            } else {
                                $('#installFirst').hide();
                                $('#installError').show();
                            }
                        }, 'json');
                    });
        });
    </script>
    <#else>
    <noscript>Not have Script!</noscript>
    </#if>
</html>