'''
Created on Nov 17, 2012

@author: pavel
'''

import os
import sys

#stats_folder = sys.argv[1]
stats_folder = '/home/pavel/tmp/sem-loc/Random-Mex/Stats/'
stats_prefix = '.stat.txt'

def acc_stats(stats_list, line):
    arr = line[:-1].split(',')
    
    if (arr[0].isdigit()):
        sem_time = float(arr[5]) if arr[5] != '0' else 1.0
        sem_size = int(arr[4])
        mex_size = int(arr[6])
        
        stats_list[0] += 1
        stats_list[1] += (1 if mex_size != sem_size else 0)
        stats_list[2] += (sem_size - mex_size)
        
        if (sem_size > 0):
            stats_list[3] += 1.0 * (sem_size - mex_size) / sem_size
        
        stats_list[4] += (float(arr[7]) / sem_time)
        '''
        if (mex_size < sem_size):
            print arr
        '''
    return stats_list

#averaging some stats for a given ontology
def average_stats(stats_list):
    if (stats_list[1] > 0):
        stats_list[2] = stats_list[2] * 1.0 / stats_list[1]
        stats_list[3] = stats_list[3] * 1.0 / stats_list[1]
    stats_list[4] = stats_list[4] * 1.0 / stats_list[0]    
    return stats_list

#compute the stats (via reduce) for one ontology stats file
def compute_ont_stats(ont_file_name):
    with open(stats_folder + ont_file_name, 'r') as ont_file:
        #format (#tests, #differences, abs_diff_size, rel_diff_size, avg_time_ratio)
        stats_list = [ont_file_name.split(stats_prefix)[0]] + average_stats(reduce(acc_stats, ont_file, [0, 0, 0, 0, 0])) 
    return stats_list

#returns true if there's a difference for this ontology   
def diff_there(stats_list):
    return stats_list[2] > 0    

#print compute_ont_stats('cao.stat.txt')
diff_count = 0    
#Format: n,SigSize,SynModSize,SynModTime,SemModSize,SemModTime,MexModSize,MexModTime
for ont_stats in map(compute_ont_stats, os.listdir(stats_folder)):
    if diff_there(ont_stats):
        #print ont_stats
        print [ont_stats[0]] + ont_stats[1:3] + map(lambda f: "{0:.2f}".format(f), ont_stats[3:6])
        diff_count += 1
        
print str(diff_count) + ' ontologies with differences'   