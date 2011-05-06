package com.inthinc.pro.automation.utils;

import java.io.StringWriter;

public class Xpath {

    public static Xpath start() {
        return new Xpath();
    }

    private StringWriter writer;

    private Xpath() {
        writer = new StringWriter();
        writer.write("/");
    }

    public Xpath a() {
        return a("");
    }

    public Xpath a(String... strings) {
        writer.write("/a");
        inner(strings);
        return this;
    }

    public Xpath body() {
        writer.write("/body");
        return this;
    }

    public Xpath button() {
        return button("");
    }

    public Xpath button(String... strings) {
        writer.write("/button");
        inner(strings);
        return this;
    }

    public Xpath dd() {
        return dd("");
    }

    public Xpath dd(String... strings) {
        writer.write("/dd");
        inner(strings);
        return this;
    }

    public Xpath div() {
        return div("");
    }

    public Xpath div(String... strings) {
        writer.write("/div");
        inner(strings);
        return this;
    }

    public Xpath dl() {
        return dl("");
    }

    public Xpath dl(String... strings) {
        writer.write("/dl");
        inner(strings);
        return this;
    }

    public Xpath form() {
        return form("");
    }

    public Xpath form(String... strings) {
        writer.write("/form");
        inner(strings);
        return this;
    }

    public Xpath h1() {
        writer.write("/h1");
        return this;
    }

    public Xpath h1(String... strings) {
        writer.write("h1");
        inner(strings);
        return this;
    }

    public Xpath h2() {
        writer.write("/h2");
        return this;
    }

    public Xpath h2(String... strings) {
        writer.write("h2");
        inner(strings);
        return this;
    }

    public Xpath h3() {
        writer.write("/h3");
        return this;
    }

    public Xpath h3(String... strings) {
        writer.write("/h3");
        inner(strings);
        return this;
    }

    public Xpath h4() {
        writer.write("/h4");
        return this;
    }

    public Xpath h4(String... strings) {
        writer.write("/h4");
        inner(strings);
        return this;
    }

    public Xpath html() {
        writer.write("/html");
        return this;
    }

    public Xpath img() {
        writer.write("/img");
        return this;
    }

    public Xpath img(String... strings) {
        writer.write("/img");
        inner(strings);
        return this;
    }

    private void inner(String... strings) {
    	for (String string : strings) {
        	if (!string.equals("")){
	            writer.write("[");
	            writer.write(string);
	            writer.write("]");
        	}
        }
    }

    public Xpath input() {
        return input("");
    }

    public Xpath input(String... strings) {
        writer.write("/input");
        inner(strings);
        return this;
    }

    public Xpath label() {
        return label("");
    }

    public Xpath label(String... strings) {
        writer.write("/label");
        inner(strings);
        return this;
    }

    public Xpath li() {
        return li("");
    }

    public Xpath li(String... strings) {
        writer.write("/li");
        inner(strings);
        return this;
    }

    public Xpath optgroup() {
        return optgroup("");
    }

    public Xpath optgroup(String... strings) {
        writer.write("/optgroup");
        inner(strings);
        return this;
    }

    public Xpath option() {
        return option("");
    }

    public Xpath option(String... strings) {
        writer.write("/option");
        inner(strings);
        return this;
    }

    public Xpath p() {
        writer.write("/p");
        return this;
    }

    public Xpath p(String... strings) {
        writer.write("p");
        inner(strings);
        return this;
    }

    public Xpath script() {
        return script("");
    }

    public Xpath script(String... strings) {
        writer.write("/script");
        inner(strings);
        return this;
    }

    public Xpath select() {
        return select("");
    }

    public Xpath select(String... strings) {
        writer.write("/select");
        inner(strings);
        return this;
    }

    public Xpath span() {
        return span("");
    }

    public Xpath span(String... strings) {
        writer.write("/span");
        inner(strings);
        return this;
    }

    public Xpath strong() {
        return td("");
    }

    public Xpath strong(String... strings) {
        writer.write("/strong");
        inner(strings);
        return this;
    }

    public Xpath table() {
        return table("");
    }

    public Xpath table(String... strings) {
        writer.write("/table");
        inner(strings);
        return this;
    }
    
    public Xpath thead() {
        return thead("");
    }

    public Xpath thead(String... strings) {
        writer.write("/thead");
        inner(strings);
        return this;
    }

    public Xpath tbody() {
        return tbody("");
    }

    public Xpath tbody(String... strings) {
        writer.write("/tbody");
        inner(strings);
        return this;
    }

    public Xpath td() {
        return td("");
    }

    public Xpath td(String... strings) {
        writer.write("/td");
        inner(strings);
        return this;
    }

    public Xpath text() {
        return ul("");
    }

    public Xpath text(String... strings) {
        writer.write("/text()");
        inner(strings);
        return this;
    }

    public Xpath th() {
        return th("");
    }

    public Xpath th(String... strings) {
        writer.write("/th");
        inner(strings);
        return this;
    }

    public String toString() {
        return writer.toString();
    }

    public Xpath tr() {
        return tr("");
    }

    public Xpath tr(String... strings) {
        writer.write("/tr");
        inner(strings);
        return this;
    }

    public Xpath ul() {
        return ul("");
    }

    public Xpath ul(String... strings) {
        writer.write("/ul");
        inner(strings);
        return this;
    }

}
