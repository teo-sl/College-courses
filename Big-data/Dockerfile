FROM ubuntu:18.04

ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8


RUN apt-get --yes -qq update \
 && apt-get --yes -qq upgrade \
 && apt-get --yes -qq install \
                      bzip2 \
                      cmake \
                      cpio \
                      curl \
                      g++ \
                      gcc \
                      gfortran \
                      git \
                      gosu \
                      libblas-dev \
                      liblapack-dev \
                      python3-dev \
                      python3-pip \
                      virtualenv \
                      wget \
                      zlib1g-dev \
                      vim       \
                      htop      \
			&& apt-get --yes update  \								
			&& apt-get --yes install -y openjdk-8-jdk \
			&& apt-get --yes install -y ant \
			&& apt-get --yes clean \
			&& apt-get --yes -qq clean \
			 && rm -rf /var/lib/apt/lists/*


RUN wget https://download.open-mpi.org/release/open-mpi/v4.0/openmpi-4.0.1.tar.gz \
	&& tar -xvf openmpi-4.0.1.tar.gz \
  && cd openmpi-4.0.1 && ./configure --enable-mpi-java &&  make all install && ldconfig 


CMD [ "/bin/bash" ]