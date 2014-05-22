#!/bin/sh

/mnt/raid/glassfish3/bin/asadmin stop-domain domain2
rm -rf /mnt/raid/glassfish3/glassfish/domains/domain2/autodeploy/*
cp ./target/*.war /mnt/raid/glassfish3/glassfish/domains/domain2/autodeploy/
/mnt/raid/glassfish3/bin/asadmin start-domain domain2
