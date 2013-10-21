package ua.dp.stud.StudPortalLib.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 08.07.13
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class CustomFunctionsTest {
    @Test
    public void truncateHtmlTest() {
        StringBuilder sourceString = new StringBuilder();
        sourceString.append("<h6><a href='' name='name1'>Экспозиция в зале полиции</a></h6>\n")
                .append("[p]Экспозиция музея начинается с зала истории Луганской полиции. Появление первых представителей правоохранительных органов в нашем крае напрямую связано со строительством Луганского литейно-пушечного завода в 1795 г. С высочайшим утверждением 3 сентября 1882 г. Положения о возведении поселка литейного завода в степень уездного города в Луганске учреждается полицейская команда в составе 20 человек. [/p]<center><table><tr><td>[url=http://localhost/lugmia/museum/uploads/images/default/d24d1801bd.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/d24d1801bd.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]d24d1801bd.jpg (82.71 Kb)[/img][/url]</td><td>[url=http://localhost/lugmia/museum/uploads/images/default/c036e00f50.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/c036e00f50.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]c036e00f50.jpg (140.64 Kb)[/img][/url]</td><td>[url=http://localhost/lugmia/museum/uploads/images/default/db8d1a3570.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/db8d1a3570.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]db8d1a3570.jpg (159.67 Kb)[/img][/url]</td><td>[url=http://localhost/lugmia/museum/uploads/images/default/239cf3b6bb.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/239cf3b6bb.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]239cf3b6bb.jpg (96.35 Kb)[/img][/url]</td></tr></table></center>\n")
                .append("[p]К началу ХХ века в Луганске уже функционировало 4 городских полицейских участка, а также Славяносербское уездное управление, находящееся на Банковской улице, арестный дом, рассчитанный на 24 человека, тюремный замок. Городскому полицейскому управлению подчинялись участковые приставы, полицейские, околоточные надзиратели, городовые. Расчет количества полицейских чинов в городах велся по норме – не менее одного городового на 500 жителей. Вступая в должность, полицейские давали клятвенное обещание, полный текст которого, как и места размещения учреждений полиции, можно увидеть в зале музея. Музей располагает копиями полицейских рапортов, донесений, сообщений «филеров» (специальных агентов жандармерии, осуществляющих надзор за политически неблагонадежными гражданами), фрагменты уголовного дела на беглых рабочих Луганского литейного завода, датированных 1807 г. и многие другие.[/p]\n")
                .append("<center><table><tr></tr></table></center>\n")
                .append("[p]В феврале 1905 года в Луганске состоялась первая всеобщая забастовка рабочих, тем самым назрела необходимость создания в Луганске жандармского управления, которое было размещено в Каменном Броде. В зале можно ознакомиться с материалами о знаменитых деятелях революционного движения, отбывавших наказание в  тюремном замке. Г.И. Петровский, К.Е. Ворошилов, Я. Моргенштейн и многие другие были узниками камер Луганской тюрьмы. Здесь же представлены распорядок дня и прогулок, расписание кушаний для политзаключенных, которые они могли заказать за свой счет.[/p]\n")
                .append("[p]На К.Е.Ворошилова партией большевиков была возложена обязанность создания и обучения боевых рабочих дружин. Опираясь на них, ставших прообразом Красной Гвардии, большевики устанавливали и охраняли революционный порядок в поселках и городах уезда.[/p]\n")
                .append("<div class='next'><a href='http://localhost/lugmia/museum/static/main-page-of-lugansk-police-museum.html' title='Перейти к главной странице'>Перейти к главной странице</a> | <a href='http://localhost/lugmia/museum/static/karta_saita.html' title='Карта сайта'>Карта сайта</a> | <a href='http://localhost/lugmia/museum/static/zal-of-police-history-of-lugansk-police-museum.html' title='Перейти к залу истории милиции'>Перейти к залу истории милиции</a></div>");
        StringBuilder result = new StringBuilder();
        result.append("<h6><a href='' name='name1'>Экспозиция в зале полиции</a></h6>\n")
                .append("[p]Экспозиция музея начинается с зала истории Луганской полиции. Появление первых представителей правоохранительных органов в нашем крае напрямую связано со строительством Луганского литейно-пушечного завода в 1795 г. С высочайшим утверждением 3 сентября 1882 г. Положения о возведении поселка литейного завода в степень уездного города в Луганске учреждается полицейская команда в составе 20 человек. [/p]<center><table><tr><td>[url=http://localhost/lugmia/museum/uploads/images/default/d24d1801bd.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/d24d1801bd.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]d24d1801bd.jpg (82.71 Kb)[/img][/url]</td><td>[url=http://localhost/lugmia/museum/uploads/images/default/c036e00f50.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/c036e00f50.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]c036e00f50.jpg (140.64 Kb)[/img][/url]</td><td>[url=http://localhost/lugmia/museum/uploads/images/default/db8d1a3570.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/db8d1a3570.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]db8d1a3570.jpg (159.67 Kb)[/img][/url]</td><td>[url=http://localhost/lugmia/museum/uploads/images/default/239cf3b6bb.jpg\" onclick=\"return hs.expand (this)\"  class=\"highslide][img=\"http://localhost/lugmia/museum/uploads/images/default/thumb/239cf3b6bb.jpg\" class=\"nimg\" border=\"0\" align=\"left\"]239cf3b6bb.jpg (96.35 Kb)[/img][/url]</td></tr></table></center>\n")
                .append("[p]К началу ХХ века в Луганске уже функционировало 4 городских полицейских участка, а также Славяносербское уездное управление, находящееся на Банковской улице, арестный дом, рассчитанный на 24 человека, тюремный замок. Городскому полицейскому управлению подчинялись участковые приставы, полицейские, околоточные надзиратели, городовые. Расчет количества полицейских чинов в городах велся по норме – не менее одного городового на 500 жителей. Вступая в должность, полицейские давали клятвенное обещание, полный текст которого, как и места размещения учреждений полиции, можно увидеть в зале музея. Музей располагает копиями полицейских рапортов, донесений, сообщений «филеров» (специальных агентов жандармерии, осуществляющих надзор за политически неблагонадежными гражданами), фрагменты уголовного дела на беглых рабочих Луганского литейного завода, датированных 1807 г. и многие другие.[/p]\n")
                .append("<center><table><tr></tr></table></center>\n")
                .append("[p]В феврале 1905 года в Луганске состо");
        assertEquals(result.toString(), CustomFunctions.truncateHtml(sourceString.toString(), 2500));
    }

    @Test
    public void getCreationDateTest() {
        Date date = DateTime.parse("31.12.2012 09:00:00", DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss")).toDate();
        String result = CustomFunctions.getCreationDate(date);
        assertNotNull(result);
    }
}
