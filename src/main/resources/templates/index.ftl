<#import "template.ftl" as layout />
<@layout.mainLayout>

<a href="/jpvocabulary?action=new" class="btn btn-outline-success" role="button">New</a>

<p>&nbsp&nbsp</p>

  <select class="selectpicker" id="selectDate" name="selectDate" onchange="toSelectDate(this)">
    <option value="">全部</option>
    <#list data as datas>
    <option value="${datas}">${datas}</option>
    </#list>
  </select>

<input type="hidden" id="beforevalue" name="beforevalue" value="${beforevalue}">

<table class="table table-bordered-sm" style="width:100%>
    <thead class="thead-light">
    <tr style="white-space: nowrap;">
        <th scope="col">日期</th>
        <th scope="col">字</th>
        <th scope="col">詞性</th>
        <th scope="col">解釋</th>
        <th scope="col">例句</th>
        <th scope="col">解釋</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list jpvocabularys as jpv>
    <tr style="white-space: nowrap;">
        <td>${jpv.date}</td>
        <td>${jpv.kanjiRuby}</td>
        <td>${jpv.type}</td>
        <td>${jpv.kanjiTranslate}</td>
        <td>${jpv.examplehiraganaRuby}</td>
        <td>${jpv.exampleTranslate}</td>
        <td>
            <div class="btn-group">
             <a href="/jpvocabulary?action=edit&id=${jpv.id}" class="btn btn-outline-dark" role="button">Edit</a>
              <a href="/delete?id=${jpv.id}" class="btn btn-outline-danger" role="button">Delete</a>
            </div>

        </td>
    </tr>
    </#list>
    </tbody>
</table>

<script>
document.getElementById("selectDate").value = document.getElementById("beforevalue").value;
function toSelectDate(selectObject){
        window.location.href = "/select?date="+selectObject.value;
        }
</script>

</@layout.mainLayout>