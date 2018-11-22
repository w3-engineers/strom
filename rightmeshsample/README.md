# RightMesh Sample
This is a sample application to demonstrate the usage of RM related support from 
the framework side.
* Abstracts AIDL enabled service approach. Developers get only few API from 
[BaseRmDataSource](https://code.leftofthedot.com/azim/android-framework/blob/rm/framework/src/main/java/core/left/io/framework/application/data/remote/BaseRmDataSource.java)
* Generates an event upon Peer discovery. Developers does not need to implement 
peer discovery process separately

We are following a converntion so that developers must maintain their userName, 
password inside local.properties (git ignored) and their App Key and Port number 
inside gradle.properties (public and available for all project memebers, also the 
key should have been shared to all developers from RM dashboard)

We have some more plan to work to enhance developers experience to configure RM
related projects. You must always use [this line](https://code.leftofthedot.com/azim/android-framework/blob/rm/build.gradle#L6)
to retrieve and generate RM related credentials.

We have assumed that our projects going to use a single port for each projects
(normally it should not change but it does change we will make adjustment based 
on requirement)

*Expecting feedback from the developers to make it as a better usable for developers.
It would give us opportunity to update or fix things at a single place and save 
our repeated effort.*