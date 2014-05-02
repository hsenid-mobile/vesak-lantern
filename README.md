vesak-lantern
=============

SDP application for Vesak Lantern 2014

Development Guide
=================
1. Download and Install [Play 2.2.2](http://www.playframework.com/download) and add its path to the PATH.
2. Type `play` to start the play console.
3. Use `stage` command to create the deployment.
4. Use `idea` to generate Idea Project.

Refer *conf/routes* file for available requests paths.

Algorithm
===========
R = Ro(1 - Wc) + Rv * Wc

Sample Mo Message
=====================

<pre>

curl http://localhost:9000/sms -H "Content-Type: application/json" -X POST -d'
{
"version":"1.0",
"message":"test",
"sourceAddress":"tel:94776177393",
"requestId":"12345678910",
"applicationId": "APP_000001"}'

</pre>
