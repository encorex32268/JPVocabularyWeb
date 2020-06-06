<#import "template.ftl" as layout />
<@layout.mainLayout>

<a href="/jpvocabulary?action=new" class="btn btn-secondary float-right mb-1" role="button">New JPvocabulary</a>


 <select id="selectDate" name="selectDate" onchange="toSelectDate(this)">
    <#list data as datas>
    <option value="${datas}">${datas}</option>
    </#list>
  </select>


<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">日期</th>
        <th scope="col">漢字</th>
        <th scope="col">詞性</th>
        <th scope="col">解釋</th>
        <th scope="col">例句</th>
        <th scope="col">解釋</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list jpvocabularys as jpv>
    <tr>
        <td>${jpv.date}</td>
        <td>${jpv.kanjiRuby}</td>
        <td>${jpv.type}</td>
        <td>${jpv.kanjiTranslate}</td>
        <td>${jpv.examplehiraganaRuby}</td>
        <td>${jpv.exampleTranslate}</td>
        <td>
            <a href="/jpvocabulary?action=edit&id=${jpv.id}" class="btn btn-secondary float-right mr-2" role="button">Edit</a>
            <a href="/delete?id=${jpv.id}" class="btn btn-danger float-right mr-2" role="button">Delete</a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>

<script>

function toSelectDate(selectObject){
        window.location.href = "/select?date="+selectObject.value;
        }
</script>

</@layout.mainLayout>