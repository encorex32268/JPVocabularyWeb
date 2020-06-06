<#macro mainLayout title="日文單字">
<!doctype html>
<html lang="en">
    <head>
        <title>${title}</title>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

        <!-- Unused -->
        <link rel="stylesheet" href="/static/node_modules/bootstrap-select/dist/css/bootstrap-select.min.css">

    </head>
    <body>
    <div class="container">
        <div class="row m-1">
            <h3>日本語</h3>
        </div>
        <div class="row m-1">
            <#nested/>
        </div>
    </div>
</body>
</html>
</#macro>

