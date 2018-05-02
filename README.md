# FonReports
用於產生報告樣板之工具集，未來因應需求持續擴充
說明如何使用及注意事項

## 資料結構

| 選項 | 描述 |
|:------ |:----------- |
| bin   | 主程式及 Mapping 設定檔案位置。|
| libs | 其他依賴之位置。 |
| template | 樣板及素材放置位置。 |

## 指令執行
範例執行可於 **bin** 資料夾內找到 **start.bat** 作為範例修改。

```sh=1
java -jar fon-reports-cli-0.5.0.jar -b D:/FonReports/
-c bin/ReportMapping.xml -t template/template.docx -o test.docx
```

各指令請參考以下表格：

| 選項 | 描述 | 是否必要 | 預設值 |
|:------ |:----------- |:-----------:|:-----------:|
| -b   | 主要工作資料夾位置。| 是 | - |
| -c | XML 設定檔位置，**相對於主要工作資料夾**。 | 是 | - |
| -t | 讀取樣板檔，**相對於主要工作資料夾**。 | 是 | - |
| -o | 輸出檔案，**相對於主要工作資料夾**。 | 否 | Output.docx |

## ReportMapping.xml 檔案說明

目前位於 **bin/** 底下的關鍵字設定檔 (可根據 -c 指令調整位置)，其內容如下所示：

- images 圖片關鍵字定義:
    - 底下 mapping 可無限增加，檔案路徑相對於 **-b 指令指定路徑**
- texts 文字關鍵字定義:
    - 底下 mapping 可無限增加

```xml=1
<?xml version="1.0" encoding="UTF-8"?>
<ReportMapping>
	<images>
		<mapping id="DaanCatchemts" value="word/imgs/DaanCatchments.png" />
		<mapping id="Test" value="word/imgs/Test.PNG" />
	</images>
	<texts>
		<mapping id="rmo" value="第十河川局" />
		<mapping id="basin" value="淡水河" /> 
		<mapping id="year" value="2017" />
		<mapping id="catchmentName" value="大安" />
		<mapping id="catchmentId" value="C1140M101S" />
	</texts>
</ReportMapping>
```

## 樣板檔製作注意事項

- 圖片關鍵字: 以 **#{}** 標示，範例 #{DaanCatchemts}
    - 請注意圖片關鍵字會把**整段落移除**，請慎選放置位置
- 文字關鍵字: 以 **${}** 標示，範例 ${rmo}
- 各關鍵字請盡量由提供的範例 **template/template.docx** 內**複製整個標籤**，再修改內中關鍵字，而非手動輸入。因為 Office 判定機制的問題，手動輸入有時候會被判定成三個字