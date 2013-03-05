<%@ Page Language="VB" AutoEventWireup="false" CodeFile="Sample1.aspx.vb"
    Inherits="UpdatePanel_Sample1" Debug="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>FusionCharts Examples - Using ASP.NET.AJAX Update Panel and Grid</title>
    <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../assets/ui/js/jquery.min.js"></script>
    <script type="text/javascript" src="../assets/ui/js/lib.js"></script>
    <script type="text/javascript" src="../FusionCharts/FusionCharts.js"></script>
    <style type="text/css">
        h2.headline
        {
            font: normal 110%/137.5% "Trebuchet MS" , Arial, Helvetica, sans-serif;
            padding: 0;
            margin: 25px 0 25px 0;
            color: #7d7c8b;
            text-align: center;
        }
        p.small
        {
            font: normal 68.75%/150% Verdana, Geneva, sans-serif;
            color: #919191;
            padding: 0;
            margin: 0 auto;
            width: 664px;
            text-align: center;
        }
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <div id="wrapper">
        <asp:ScriptManager ID="ScriptManager1" runat="server">
            <Scripts>
                <asp:ScriptReference Path="~/UpdatePanel/updatepanelhook.fusioncharts.js" />
            </Scripts>
        </asp:ScriptManager>
        <div id="header">
            <div class="logo">
                <a class="imagelink" href="../Default.aspx">
                    <img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75"
                        alt="FusionCharts XT logo" /></a></div>
            <h1 class="brand-name">FusionCharts XT</h1>
            <h1 class="logo-text">
                Using ASP.NET(VB) Update Panel and Grid</h1>
        </div>
        <div class="content-area">
            <div id="content-area-inner-main">
                <p class="text" align="center">
                </p>
                <div class="gen-chart-render">
                    <table width="770" cellpadding="0">
                        <tr>
                            <td align="center" valign="middle" colspan="2">
                                <h2 class="headline">
                                    Please select a Factory from the options below to show data in the grid and the chart</h2>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle" colspan="2" style="border: 1px dotted #dedede;
                                height: 60px;">
                                <asp:UpdatePanel ID="FactorySelector" runat="server">
                                    <ContentTemplate>
                                        <asp:RadioButtonList ID="RadioButtonList1" runat="server" AutoPostBack="True" Height="40px"
                                            OnSelectedIndexChanged="RadioButtonList1_SelectedIndexChanged" Width="400px"
                                            RepeatDirection="Horizontal" Style="font-weight: bold; font-size: 14px; font-family: Verdana"
                                            ForeColor="#404040">
                                        </asp:RadioButtonList>
                                    </ContentTemplate>
                                </asp:UpdatePanel>
                            </td>
                        </tr>
                        <tr>
                            <td valign="middle" style="width: 320px; height: 299px; border-right: #cdcdcd 1px dotted;
                                border-top: #cdcdcd 1px dotted; border-left: #cdcdcd 1px dotted; border-bottom: #cdcdcd 1px solid;"
                                align="center">

                                <div style="position:relative;">
                                <asp:UpdateProgress ID="UpdateProgress1" runat="server" DisplayAfter="1" style="position:absolute; top:150px;left:148px;">
                                    <ProgressTemplate>
                                        <img src="Images/loading.gif" />
                                    </ProgressTemplate>
                                </asp:UpdateProgress>

                                <asp:UpdatePanel ID="GridUP" runat="server">
                                    <ContentTemplate>
                                        <div style="width: 318px; height: 350px; overflow: auto; overflow-x: hidden; overflow-y: auto;">
                                            <asp:GridView ID="GridView1" runat="server" BackColor="White" BorderColor="#DEDFDE"
                                                BorderStyle="None" BorderWidth="1px" CellPadding="4" ForeColor="Black" GridLines="Vertical"
                                                Height="350px" Width="300px" AutoGenerateColumns="False">
                                                <FooterStyle BackColor="#CCCC99" />
                                                <RowStyle BackColor="#EFEFEF" Font-Names="Verdana" Font-Size="10px" />
                                                <PagerStyle BackColor="#F7F7DE" ForeColor="Black" HorizontalAlign="Right" />
                                                <SelectedRowStyle BackColor="#CE5D5A" Font-Bold="True" ForeColor="White" />
                                                <HeaderStyle BackColor="#E0E0E0" Font-Bold="True" ForeColor="#404040" Font-Names="Verdana"
                                                    Font-Size="11px" />
                                                <AlternatingRowStyle BackColor="White" />
                                                <Columns>
                                                    <asp:BoundField DataField="DatePro" DataFormatString="{0:dd/MM/yyyy}" HeaderText="Date">
                                                        <ItemStyle HorizontalAlign="Center" />
                                                    </asp:BoundField>
                                                    <asp:BoundField DataField="Quantity" HeaderText="Quantity">
                                                        <ItemStyle HorizontalAlign="Center" />
                                                    </asp:BoundField>
                                                </Columns>
                                            </asp:GridView>
                                        </div>
                                    </ContentTemplate>
                                    <Triggers>
                                        <asp:AsyncPostBackTrigger ControlID="RadioButtonList1" EventName="SelectedIndexChanged" />
                                    </Triggers>
                                </asp:UpdatePanel>
                                </div>
                            </td>
                            <td valign="middle" style="width: 440px; height: 350px; border: 1px dotted #dedede;">
                                <div  style="position:relative;"> 
                                 <asp:UpdateProgress ID="UpdateProgress2" runat="server" DisplayAfter="1" style="position:absolute; top:150px;left:218px;">
                                    <ProgressTemplate>
                                        <img src="Images/loading.gif"  />
                                    </ProgressTemplate>
                                </asp:UpdateProgress>
                                
                                <asp:UpdatePanel ID="FusionChartsUP" runat="server">
                                    <ContentTemplate>
                                        <asp:Panel ID="Panel1" runat="server" Height="350px" Width="440px">
                                        </asp:Panel>
                                    </ContentTemplate>
                                </asp:UpdatePanel>
                               </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <p>&nbsp;
                    </p>
                <div class="underline-dull">
                </div>
                <div>
                </div>
                <p>&nbsp;
                    </p>
                <p class="small">
                    <!--<p class="small">This dashboard was created using FusionCharts XT, FusionWidgets v3 and FusionMaps v3 You are free to reproduce and distribute this dashboard in its original form, without changing any content, whatsoever. <br />
            &copy; All Rights Reserved</p>
          <p>&nbsp;</p>-->
                </p>
            </div>
        </div>
    </div>
    <div id="footer">
        <ul>
            <li><a href="../Default.aspx"><span>&laquo; Back to list of examples</span></a></li>
            <li class="pipe">|</li>
            <li><a href="../NoChart.html"><span>Unable to see the chart above?</span></a></li>
        </ul>
    </div>
    </form>
</body>
</html>
