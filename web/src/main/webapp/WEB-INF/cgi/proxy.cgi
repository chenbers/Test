#!/usr/bin/env python


"""This is a blind proxy that we use to get around browser
restrictions that prevent the Javascript from loading pages not on the
same server as the Javascript.  This has several problems: it's less
efficient, it might break some sites, and it's a security risk because
people can use this proxy to browse the web and possibly do bad stuff
with it.  It only loads pages via http and https, but it can load any
content type. It supports GET and POST requests."""

import urllib2
import cgi
import sys, os
import logging


logging.basicConfig(level=logging.DEBUG, filename='debug.log',
                    format='%(asctime)s %(levelname)s: %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')



logging.debug('testing')

logging.debug('This is a debug message.')

# Designed to prevent Open Proxy type stuff.
allowedHosts = ['192.168.1.219:8086']

method = os.environ["REQUEST_METHOD"]

if method == "POST":
    qs = os.environ["QUERY_STRING"]
    d = cgi.parse_qs(qs)
    if d.has_key("url"):
        url = d["url"][0]
    else:
        url = "http://192.168.1.219:8086"
else:
    fs 				= cgi.FieldStorage()
    url 			= fs.getvalue('url', "http://192.168.1.219:8086")
    service 		= fs.getvalue('service', 'WMS' )
    version 		= fs.getvalue('version','1.1.0')
    request 		= fs.getvalue('request','GetFeatureInfo')
    srs 			= fs.getvalue('srs','EPSG%253A4326')
    feature_count 	= fs.getvalue('feature_count', '10')
    layers			= fs.getvalue('layers', 'streets')
    query_layers	= fs.getvalue('query_layers', 'streets')
    bbox			= fs.getvalue('bbox','WTF')
    x				= fs.getvalue('x', '')
    y				= fs.getvalue('y','')
	
	# stuff = '&x=266&y=63&height=256&width=512&'
    
    logging.debug('url: '+ url)
    logging.debug('service:  '+service)
    logging.debug('version:  '+version)
    logging.debug('request:  '+request)
    
    newUrl  = url
    newUrl += "&&service="
    newUrl += service
    newUrl += "&version="
    newUrl += version
    newUrl += "&request="
    newUrl += request
    newUrl += "&layers="
    newUrl += layers

    newUrl += "&srs=EPSG%3A4326"
    newUrl += "&feature_count="
    newUrl += feature_count
    newUrl += "&query_layers="
    newUrl += query_layers
    newUrl += "&styles="
    newUrl += "&bbox="
    newUrl += bbox
    newUrl += "&x="
    newUrl += x
    newUrl += "&y="
    newUrl += y
    
    newUrl += "&height=256&width=512&"
    
    logging.debug('newurl:  '+newUrl)
    

try:
    host = url.split("/")[2]
    if allowedHosts and not host in allowedHosts:
        print "Status: 502 Bad Gateway"	
        print "Content-Type: text/plain"
        print
        print "This proxy does not allow you to access that location (%s)." % (host,)
        print
        print os.environ
  
    elif url.startswith("http://") or url.startswith("https://"):
    
        if method == "POST":
            length = int(os.environ["CONTENT_LENGTH"])
            headers = {"Content-Type": os.environ["CONTENT_TYPE"]}
            body = sys.stdin.read(length)
            r = urllib2.Request(url, body, headers)
            y = urllib2.urlopen(r)
        else:
            y = urllib2.urlopen(newUrl)
        
        # print content type header
        i = y.info()
        if i.has_key("Content-Type"):
            print "Content-Type: %s" % (i["Content-Type"])
        else:
            print "Content-Type: text/plain"
        print
        
        print y.read()
        
        y.close()
    else:
        print "Content-Type: text/plain"
        print
        print "Illegal request."
        
except Exception, E:
    print "Status: 500 Unexpected Error"
    print "Content-Type: text/plain"
    print 
    print "Some unexpected error occurred. Error text was:", E
