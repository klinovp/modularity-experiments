'''
Created on Nov 17, 2012

@author: pavel
'''

import os
import sys

#stats_folder = sys.argv[1]
stats_folder = '/home/pavel/tmp/sem-loc/Random-Mex-All/Stats/'
stats_prefix = '.stat.txt'

_base_module_size_col_ = 4
_base_module_time_col_ = 5
_module_size_col_ = 6
_module_time_col_ = 7

def acc_stats(stats_list, line):
    arr = line[:-1].split(',')
    
    if (arr[0].isdigit()):
        base_mod_time = float(arr[_base_module_time_col_]) if arr[_base_module_time_col_] != '0' else 1.0
        base_mod_size = int(arr[_base_module_size_col_])
        mod_size = int(arr[_module_size_col_])
        mod_time = float(arr[_module_time_col_])
        diff = base_mod_size - mod_size;
        
        stats_list[0] += 1
        stats_list[1] += (1 if mod_size != base_mod_size else 0)
        stats_list[2] += diff
        
        if (base_mod_size > 0):
            rel_diff = 1.0 * diff / base_mod_size
            stats_list[3] += rel_diff
	
				    #update min and max for both absolute and relative differences
				    #min abs
            stats_list[4] = min(stats_list[4], diff)
				    #max abs
            stats_list[5] = max(stats_list[5], diff)
				    #min rel
            stats_list[6] = min(stats_list[6], rel_diff)
				    #max rel
            stats_list[7] = max(stats_list[7], rel_diff)
        
				#time ratio
        stats_list[8] += (mod_time / base_mod_time)
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

    stats_list[8] = stats_list[8] * 1.0 / stats_list[0]    
    return stats_list

#compute the stats (via reduce) for one ontology stats file
def compute_ont_stats(ont_file_name):
    with open(stats_folder + ont_file_name, 'r') as ont_file:
        #format (#tests, #differences, abs_diff_size, rel_diff_size, min_abs_diff, max_abs_diff, min_rel_diff, max_rel_diff, avg_time_ratio)
        stats_list = [ont_file_name.split(stats_prefix)[0]] + average_stats(reduce(acc_stats, ont_file, [0, 0, 0, 0, 0, 0, 0, 0, 0])) 
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
        print [ont_stats[0]] + ont_stats[1:3] + map(lambda f: "{0:.2f}".format(f), ont_stats[3:])
        diff_count += 1
        
print str(diff_count) + ' ontologies with differences'   
