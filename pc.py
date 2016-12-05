# -*- coding: utf-8 -*-
"""
Created on Sat Dec  3 17:37:01 2016

@author: AntonioPeralta13
"""

import matplotlib.pyplot as plt
import matplotlib.pyplot as plt2

def main():
    Test_sizes = [5,10,15]
    PC = [121251016,111454651,109672013]
    PC2 = [119231844,110379800,108365289]
   
    plt.plot(Test_sizes, PC, label='Solution 1')
    plt.scatter(Test_sizes, PC)
    plt.plot(Test_sizes, PC2, label='Solution 2')
    plt.scatter(Test_sizes, PC2, c='g')
    plt.title("Average Time Consumer Waits")
    plt.xlabel('Number of Producers')
    plt.ylabel('Average Time(Nanotime)')
    plt.legend()
    plt.show()
    
    sizes = [5,10,15]
    PC = [121178139,111447516,109675814]
    PC2 = [119266867,110382146,108385383]
   
    plt2.plot(sizes, PC, label='Solution 1')
    plt2.scatter(sizes, PC)
    plt2.plot(sizes, PC2, label='Solution 2')
    plt2.scatter(sizes, PC2, c='g')
    plt2.title("Average time Producer Produces Item")
    plt2.xlabel('Number of Producers')
    plt2.ylabel('Average Time(Nanotime)')
    plt2.legend()
    plt2.show()
    
main()

