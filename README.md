# LABAC: Location-Aware Attribute-Based Access Control

This repository contains an implementation of **Location-Aware Attribute-Based Access Control (LABAC)**, based on the research paper ["LABAC: A Location-Aware Attribute-Based Access Control Model" (IEEE)](https://ieeexplore.ieee.org/document/7841945). The implementation is built upon the original **cpabe** (Ciphertext-Policy Attribute-Based Encryption) scheme, modified to incorporate location constraints into the access control mechanism.

## Overview
LABAC extends traditional Attribute-Based Access Control (ABAC) by introducing **spatial constraints** as a determining factor for granting access. This means that access decisions are influenced not only by user attributes but also by their geographic location at the time of request.

### Key Features:
- Enhances CP-ABE with **location-based constraints**
- Supports fine-grained **attribute-based access control**
- Ensures **secure data sharing** with policy-driven encryption

## Example Policy
A policy might require that **only doctors in Zone 1 can access patient records**:
```plaintext
(role:doctor AND location:zone1)
```

# References:

- Y. Xue, J. Hong, W. Li, K. Xue and P. Hong, "LABAC: A Location-Aware Attribute-Based Access Control Scheme for Cloud Storage," 2016 IEEE Global Communications Conference (GLOBECOM), Washington, DC, USA, 2016, pp. 1-6, doi: 10.1109/GLOCOM.2016.7841945.
- H. Li, K. Yu, B. Liu, C. Feng, Z. Qin and G. Srivastava, "An Efficient Ciphertext-Policy Weighted Attribute-Based Encryption for the Internet of Health Things," in IEEE Journal of Biomedical and Health Informatics, vol. 26, no. 5, pp. 1949-1960, May 2022, doi: 10.1109/JBHI.2021.3075995. 

# Borrorwed the base code from:

This software is a Java realization for "ciphertext-policy attribute based
encryption" (CP-ABE).

To use this software, you will need to have the Java Pairing Based Cryptography
Library(jPBC) installed (jpbc-1.2.1 is tested). You can get it from the
following page:

   http://gas.dia.unisa.it/projects/jpbc/

For more information on CP-ABE and a tutorial on using the tools, see
the project homepage:

   https://junwei.co/cpabe/

Your mush be responsible for the problem caused by using the code.

Contribution on this project is welcome! Don't be shy to improve this project
and help more people.

> Junwei Wang. *Java Realization for Ciphertext-Policy Attribute-Based Encryption.* https://github.com/junwei-wang/cpabe/, 2012

or

```tex
@article{wang2012java,
  title={Java Realization for Ciphertext-Policy Attribute-Based Encryption},
  author={Wang, Junwei},
  howpublished = {\url{https://github.com/junwei-wang/cpabe/}},
  year={2012}
}
```
