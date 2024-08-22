# Sepals

An extremely radical and experimental optimization for Minecraft server performances.

## Compatibility

Currently, sepals are compatible to almost all mods.

### Lithium

The entities cramming optimization of sepals will be auto-disabled when you used lithium and without configurations.

This feature require disable lithium's cramming to enable:

```properties
mixin.entity.collisions.unpushable_cramming=false
```

## Performance

A test was done in cao-awa personal computer, CPU: 5950x; Memory: 128G DDR4-3200MHz; OS: Windows 11; Minecraft: 1.21.

### Entities cramming

```
Use index to access element to replaced iterator
```

496 large slime cramming in a 20x20 space:

|               Environment               | tickCramming | Percent |
|:---------------------------------------:|:------------:|:-------:|
|                 Vanilla                 |   45.3 ms    |  100 %  |
|               With Sepals               |    44 ms     |  97 %   |
|              With Lithium               |   43.1 ms    |  95 %   |
| With Sepals<br/> and configured Lithium |   40.8 ms    |  90 %   |

### Weighted random

```
Use binary search to replaced vanilla weight random

-- Warning --
This feature may not provided good optimization
because it was proved by spark that slower than vanilla when constructing range table
even if the binary search almost close to 0ms
```

900 frogs cramming in a 3x3 space:

| Environment | Weighting#getRandom | Percent(Avg.) |
|:-----------:|:-------------------:|:-------------:|
|   Vanilla   |       8.4 ms        |     100 %     |
| With Sepals |       9.2 ms        |     109 %     |

### Biased long jump task

```
Use sepals long jump task impletation to replaced vanilla impletation

As mentioned above, the binary search is almost no costs
sepals impletation will construct the range table at the same time as generating targets
and used Catheter to replaced java stream

The same time, rearrange should jump conditions in frog brain, make a good performance

-- Notice --
This feature is unable to change in game runtime
required restart the server to apply changes
```

1366 frogs cramming in a 3x3 space:

|                     Environment                     | keepRunning | Percent(Avg.) |
|:---------------------------------------------------:|:-----------:|:-------------:|
|      Vanilla <br /> (LongJumpTask#keepRunning)      |   15.6 ms   |     100 %     |
| With Sepals <br /> (SepalsLongJumpTask#keepRunning) |   0.3 ms    |    0.02 %     |

|                     Environment                     | getTarget | Percent(Avg.) | Percent(in ```keepRunning```) |
|:---------------------------------------------------:|:---------:|:-------------:|:-----------------------------:|
|      Vanilla <br /> (LongJumpTask#keepRunning)      |  13.4 ms  |     100 %     |             85 %              |
| With Sepals <br /> (SepalsLongJumpTask#keepRunning) |  0.02 ms  |    0.001 %    |            0.06 %             |

### Quick sort in NearestLivingEntitiesSensor

```
Quick sort supports by fastutil to replace java tim sort
```

900 frogs cramming in a 3x3 space:

| Environment | sort (NearestLivingEntitiesSensor#sense) | Percent(Avg.) |
|------------:|:----------------------------------------:|:-------------:|
|     Vanilla |                  6.5 ms                  |     100 %     |
| With Sepals |                   3 ms                   |     46 %      |

### Frog attackable target filter

```
Rearrange the attackable conditions
let less costs predicate running first
reduce the probability of high-costs calculating
```

1366 frogs cramming in a 3x3 space:

| Environment | sort (NearestLivingEntitiesSensor#sense) | Percent(Avg.) |
|------------:|:----------------------------------------:|:-------------:|
|     Vanilla |                  6.5 ms                  |     100 %     |
| With Sepals |                   3 ms                   |     46 %      |

### Frog look-at target filter

```
Use interval's shouldRun predicate to reducing TargetPredicate test
make less raycast is the best way to optimizations at currently

-- Warning --
Not long-term stability tested, increased 'shouldRun' maybe cause behavoirs change
this feature does not be proved 100% vanilla
also it does not be proved has not vanilla in statistical significance

-- Notice --
The raycast is in TargetPredicate test
at the findFirst in LivingTargetCache when input predicate is success

but if subsequent conditions is failures, then to calculate this anymore is ueless
because even if the findFirst has found (raycast success)
but we don't used this result in subsequent contexts 
```

1366 frogs cramming in a 3x3 space:

|                                                          Environment |  sort   | Percent(Avg.) | The ```raycast``` time | The ```raycast``` percent |
|---------------------------------------------------------------------:|:-------:|:-------------:|:----------------------:|:-------------------------:|
|           Vanilla <br /> (LookAtMobWithIntervalTask$$Lambda#trigger) | 26.5 ms |     100 %     |        25.1 ms         |           94 %            |
| With Sepals <br /> (SepalsLookAtMobWithIntervalTask$$Lambda#trigger) | 3.4 ms  |     13 %      |         3.3 ms         |           97 %            |