package main

import (
	"fmt"
	"gopkg.in/alecthomas/kingpin.v2"
	"io/fs"
	"os"
	"path/filepath"
	"sync"
)

var (
	cmd  = kingpin.New("clean-projects", "Clean projects like java, node")
	path = cmd.Flag("path", "root path to clean.").
		Default(".").String()
	wg sync.WaitGroup
)

func main() {
	cmd.Version("0.0.1")
	cmd.HelpFlag.Short('h')
	command, err := cmd.Parse(os.Args[1:])
	fmt.Println(command)
	if err != nil {
		fmt.Println(err)
	}
	scanAndClean(*path)
}

func scanAndClean(path string) {
	var err error
	var toDelete []string
	err = filepath.WalkDir(path, func(path string, d fs.DirEntry, err error) error {
		// java project
		if d.IsDir() && "target" == filepath.Base(path) {
			// if pom.xml exists
			_, err = os.Stat(filepath.Join(filepath.Dir(path), "pom.xml"))
			if nil == err {
				// exists
				fmt.Println("Find path to be deleted :", path)
				toDelete = append(toDelete, path)
			} else {
				fmt.Println("Java project not valid: ", path)
				err = nil
			}
			return filepath.SkipDir
		}
		// nodejs project
		if d.IsDir() && "node_modules" == filepath.Base(path) {
			// package.json exists
			_, err = os.Stat(filepath.Join(filepath.Dir(path), "package.json"))
			if nil == err {
				fmt.Println("Find path to be deleted :", path)
				toDelete = append(toDelete, path)
			} else {
				fmt.Println("nodejs project not valid: ", path)
				err = nil
			}
			return filepath.SkipDir
		}
		// python project
		if d.IsDir() && "__pycache__" == filepath.Base(path) {
			toDelete = append(toDelete, path)
			fmt.Println("Find path to be deleted :", path)
			return filepath.SkipDir
		}
		// delete .idea .vscode
		if d.IsDir() && (".idea" == filepath.Base(path) || ".vscode" == filepath.Base(path)) {
			toDelete = append(toDelete, path)
			fmt.Println("Find path to be deleted :", path)
			return filepath.SkipDir
		}
		return err
	})
	if err != nil {
		return
	}
	wg.Add(len(toDelete))
	for _, v := range toDelete {
		v := v
		go func() {
			defer wg.Done()
			fmt.Println("deleting ", v)
			err := os.RemoveAll(v)
			if err != nil {
				return
			}
		}()
	}
	wg.Wait()
}
