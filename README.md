# NIT

Version control system.


## Getting started

To initialize the project - you need to open the terminal, go to the project folder and execute the clone command, specifying a link to the server. This command creates a hidden nit directory where the repository settings will be stored.


## Basic commands

### clone

Creates a new or clones an existing repository from the server.

	nit clone server_path

### status

Displays difference between local and server project versions: new, modified, removed files.
	
	nit status

### pull

Requests project’s version from the server:

* latest version, if nothing is specified after the command.

```
nit pull
```

* specified version, if version id is defined after the command.
	
```
nit pull version_id
```

### push

Sends local changes to the server.

	nit push
